package com.laolao.common.interceptor;

import com.laolao.common.context.SqlLogContext;
import com.laolao.common.context.UserContext;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.regex.Matcher;

@Component
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})
})
public class SqlLogInterceptor implements Interceptor {

    private static final Logger log = LoggerFactory.getLogger(SqlLogInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 1. 【核心判断】如果没有标记，直接放行，不做任何处理，性能无损耗
        // 1. 获取业务名称
        String businessAt = SqlLogContext.getBusinessName();

        // 如果为空，说明没加注解，直接放行
        if (businessAt == null) {
            return invocation.proceed();
        }

        // 1. 记录开始时间戳 (用于计算耗时)
        long startTimestamp = System.currentTimeMillis();

        Object result;
        try {
            result = invocation.proceed(); // 执行 SQL
        } finally {
            try {
                long costTime = System.currentTimeMillis() - startTimestamp;

                // 获取并格式化 SQL
                String sql = getCompleteSql(invocation);
                Integer adminId = UserContext.getCurrentId();

                log.info("[{}] Cost: {}ms | Admin: {} | SQL: {}", businessAt, costTime, adminId, sql);
            } catch (Exception e) {
                log.warn("SQL日志记录失败", e);
            }
        }
        return result;
    }

    /**
     * 获取完整 SQL (修正版：优先获取运行时被插件修改过的 SQL)
     */
    private String getCompleteSql(Invocation invocation) {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object parameterObject = args.length > 1 ? args[1] : null;

        BoundSql boundSql;
        // 【关键修改】
        // MyBatis 的 Executor.query 方法有两个重载：
        // 1. query(ms, parameter, rowBounds, resultHandler)  -> args.length = 4
        // 2. query(ms, parameter, rowBounds, resultHandler, cacheKey, boundSql) -> args.length = 6
        // PageHelper 等插件通常会调用第 2 种，把修改后的 SQL (带Limit/Count) 放在 args[5] 中
        if (args.length == 6 && args[5] instanceof BoundSql) {
            boundSql = (BoundSql) args[5];
        } else {
            // 如果是 4 个参数，说明还没有生成 BoundSql，我们手动生成（这是原始 SQL）
            boundSql = ms.getBoundSql(parameterObject);
        }

        Configuration configuration = ms.getConfiguration();
        return formatSql(configuration, boundSql);
    }

//    /**
//     * 获取完整 SQL（未处理PageHelper重复SQL问题）
//     */
//    private String getCompleteSql(Invocation invocation) {
//        Object[] args = invocation.getArgs();
//        MappedStatement ms = (MappedStatement) args[0];
//        Object parameterObject = args.length > 1 ? args[1] : null;
//        BoundSql boundSql = ms.getBoundSql(parameterObject);
//        Configuration configuration = ms.getConfiguration();
//        return formatSql(configuration, boundSql);
//    }

    /**
     * 格式化 SQL：将 ? 替换为参数值
     */
    private String formatSql(Configuration configuration, BoundSql boundSql) {
        String sql = boundSql.getSql().replaceAll("\\s+", " "); // 去除多余空格
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();

        if (!parameterMappings.isEmpty() && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));
            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    } else {
                        sql = sql.replaceFirst("\\?", "unknown");
                    }
                }
            }
        }
        return sql;
    }

    private String getParameterValue(Object obj) {
        String value;
        if (obj instanceof String) {
            value = "'" + obj + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format((Date) obj) + "'";
        } else {
            value = (obj != null) ? obj.toString() : "null";
        }
        return Matcher.quoteReplacement(value);
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}