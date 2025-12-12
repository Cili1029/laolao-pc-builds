package com.laolao.common.interceptor;

import com.laolao.common.context.UserContext;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Properties;

@Component
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class AutoFillInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object parameter = args[1];

        if (parameter == null) {
            return invocation.proceed();
        }

        SqlCommandType sqlCommandType = ms.getSqlCommandType();

        // 1. 准备时间
        LocalDateTime now = LocalDateTime.now();

        // 2. 准备操作人 ID (Integer 类型)
        Integer userId = UserContext.getCurrentId();
        // 如果未登录(null)，则默认为 0。这样 operator 就是纯正的 Integer
        Integer operator = (userId != null) ? userId : 0;

        Object entity = getRealEntity(parameter);

        if (entity != null) {
            if (sqlCommandType == SqlCommandType.INSERT) {
                // 3. 直接传入 Integer 类型的 operator
                setField(entity, "createdBy", operator);
                setField(entity, "createdAt", now);
                setField(entity, "updatedBy", operator);
                setField(entity, "updatedAt", now);
            } else if (sqlCommandType == SqlCommandType.UPDATE) {
                setField(entity, "updatedBy", operator);
                setField(entity, "updatedAt", now);
            }
        }

        return invocation.proceed();
    }

    private void setField(Object obj, String fieldName, Object value) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            // 直接赋值，因为 value 已经是 Integer 了，Entity 字段也是 Integer，类型匹配
            field.set(obj, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // 忽略无此字段的情况
        }
    }

    private Object getRealEntity(Object parameter) {
        if (!parameter.getClass().getName().startsWith("java.")
                && !parameter.getClass().getName().startsWith("java.util.")) {
            return parameter;
        }
        if (parameter instanceof Map<?, ?> map) {
            for (Object value : map.values()) {
                if (value != null
                        && !value.getClass().getName().startsWith("java.")
                        && !value.getClass().getName().startsWith("java.util.")) {
                    return value;
                }
            }
        }
        return null;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {}
}