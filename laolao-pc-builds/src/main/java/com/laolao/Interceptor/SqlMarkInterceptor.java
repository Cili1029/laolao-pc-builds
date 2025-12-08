package com.laolao.Interceptor;

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
        // 拦截Controller
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            // 检查注解(方法)
            LogSql methodAnnotation = handlerMethod.getMethodAnnotation(LogSql.class);
            // 检查注解（类）
            LogSql classAnnotation = handlerMethod.getBeanType().getAnnotation(LogSql.class);
            // 只要有 @LogSql，就开启上下文标记
            if (methodAnnotation != null || classAnnotation != null) {
                SqlLogContext.enable();
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        SqlLogContext.remove();
    }
}
