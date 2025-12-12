package com.laolao.common.websocket;

import com.laolao.common.constant.JwtClaimsConstant;
import com.laolao.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
public class WebSocketInterceptor implements HandshakeInterceptor {

    @Resource
    private JwtUtil jwtUtil;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        if (request instanceof ServletServerHttpRequest servletRequest) {
            // 从 URL 参数中获取 token
            String token = servletRequest.getServletRequest().getParameter("jwt_token");

            // 2. 校验 Token 提取userId和管理员权限
            if (StringUtils.isNotBlank(token)) {
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    int userId = Integer.parseInt(claims.get(JwtClaimsConstant.USER_ID).toString());
                    int admin = Integer.parseInt(claims.get(JwtClaimsConstant.ADMIN).toString());
                    attributes.put("userId", userId);
                    attributes.put("admin", admin);
                    return true;
                } catch (Exception e) {
                    // 校验失败，拒绝连接
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
