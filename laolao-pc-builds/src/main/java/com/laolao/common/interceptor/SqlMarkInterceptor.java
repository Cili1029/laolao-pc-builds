package com.laolao.common.interceptor;

import com.laolao.common.annotation.LogSql;
import com.laolao.common.context.SqlLogContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SqlMarkInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod handlerMethod) {
            // 1. 获取方法上的注解
            LogSql methodAnnotation = handlerMethod.getMethodAnnotation(LogSql.class);
            // 2. 获取类上的注解
            LogSql classAnnotation = handlerMethod.getBeanType().getAnnotation(LogSql.class);

            // 3. 确定最终生效的注解 (方法优先，如果方法没有，再用类的)
            LogSql targetAnnotation = (methodAnnotation != null) ? methodAnnotation : classAnnotation;

            // 4. 只要有一个不为空，就处理
            if (targetAnnotation != null) {
                String desc = targetAnnotation.description();

                // 处理描述为空的情况
                if (desc == null || desc.isEmpty()) {
                    desc = "未知业务";
                }

                // 存入 ThreadLocal
                SqlLogContext.setBusinessName(desc);
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        SqlLogContext.remove();
    }
}
