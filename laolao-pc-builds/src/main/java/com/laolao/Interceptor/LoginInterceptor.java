package com.laolao.Interceptor;

import com.laolao.common.constant.JwtClaimsConstant;
import com.laolao.common.context.BaseContext;
import com.laolao.common.properties.JwtProperties;
import com.laolao.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private JwtProperties jwtProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Cookie[] cookies = request.getCookies();
        String jwt = getJwtFromCookie(cookies);
        try {
            Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), jwt);
            long userId = Long.parseLong(claims.get(JwtClaimsConstant.USER_ID).toString());
            BaseContext.setCurrentId(userId);

            return true;
        }  catch (Exception e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        BaseContext.removeCurrentId();
    }

    public String getJwtFromCookie(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("jwt_token")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
