package com.laolao.common.interceptor;

import com.laolao.common.constant.JwtConstant;
import com.laolao.common.constant.RedisConstant;
import com.laolao.common.context.UserContext;
import com.laolao.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SignInInterceptor implements HandlerInterceptor {

    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Cookie[] cookies = request.getCookies();
        String jwt = getJwtFromCookie(cookies);
        String requestURI = request.getRequestURI();

        try {
            // 先验证jwt是否合法
            Claims claims = jwtUtil.parseJWT(jwt);
            int userId = Integer.parseInt(claims.get(JwtConstant.USER_ID).toString());

            // 合法，继续验证Redis
            String redisJwt = stringRedisTemplate.opsForValue().get(RedisConstant.User.SIGN_IN_JWT + userId);
            // 空或者不等于(顶号)
            if (redisJwt == null || !redisJwt.equals(jwt)) {
                // 删除jwt
                Cookie cookie = new Cookie("jwt_token", null);
                cookie.setHttpOnly(true);        // 防止 XSS 攻击
                cookie.setSecure(false);         // 本地开发用 false，生产环境用 true (HTTPS)
                cookie.setPath("/");             // 对整个应用有效
                cookie.setMaxAge(0);
                response.addCookie(cookie);

                // 不合法（没登陆，直接返回没登录，设置一个null用户，不用拦截）
                if (requestURI.equals("/api/common/user/profile")) {
                    UserContext.setCurrentId(null);
                    return true;
                }

                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return false;
            }
            UserContext.setCurrentId(userId);
            return true;
        } catch (Exception e) {
            // 不合法（没登陆，直接返回没登录，设置一个null用户，不用拦截）
            if (requestURI.equals("/api/common/user/profile")) {
                UserContext.setCurrentId(null);
                return true;
            }
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserContext.removeCurrentId();
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
