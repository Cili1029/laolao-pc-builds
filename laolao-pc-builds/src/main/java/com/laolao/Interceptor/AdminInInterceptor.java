package com.laolao.Interceptor;

import com.laolao.common.context.UserContext;
import com.laolao.mapper.common.UserCommonMapper;
import com.laolao.pojo.user.entity.User;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminInInterceptor implements HandlerInterceptor {

    @Resource
    private UserCommonMapper userCommonMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        int userId = UserContext.getCurrentId();
        // 后期可以改成Redis
        User user = userCommonMapper.getUser(userId);
        if (user != null && user.getAdmin() == 1) {
            // 是管理员，放行
            return true;
        }

        response.setStatus(HttpStatus.FORBIDDEN.value());
        return false;
    }
}
