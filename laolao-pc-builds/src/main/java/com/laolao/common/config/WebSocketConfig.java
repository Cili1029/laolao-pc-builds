package com.laolao.common.config;

import com.laolao.common.websocket.NotificationHandler;
import com.laolao.common.websocket.WebSocketInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Resource
    private NotificationHandler notificationHandler;
    @Resource
    private WebSocketInterceptor webSocketInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(notificationHandler, "/ws/notify") // 前端连接地址
                .addInterceptors(webSocketInterceptor)
                .setAllowedOrigins("*"); // 允许跨域
    }
}