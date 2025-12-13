package com.laolao.common.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class NotificationHandler extends TextWebSocketHandler {

    /**
     * 存储在线用户的 Session
     * Key: userId (Integer)
     * Value: WebSocketSession
     */
    private static final Map<Integer, WebSocketSession> USER_SESSIONS = new ConcurrentHashMap<>();

    /**
     * 存储在线管理员的 Session
     * Key: userId (Integer)
     * Value: WebSocketSession
     */
    private static final Map<Integer, WebSocketSession> ADMIN_SESSIONS = new ConcurrentHashMap<>();

    // 建立连接
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Integer userId = (Integer) session.getAttributes().get("userId");
        Integer admin = (Integer) session.getAttributes().get("admin");
        if (userId != null) {
            USER_SESSIONS.put(userId, session);
            if (admin == 1) {
                ADMIN_SESSIONS.put(userId, session);
            }
            System.out.println("用户上线: " + userId + (admin == 1 ? " (管理员)" : ""));
        } else {
            session.close();
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 收到心跳
        if ("ping".equals(message.getPayload())) {
            try {
                session.sendMessage(new TextMessage("pong"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 连接关闭
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Integer userId = (Integer) session.getAttributes().get("userId");
        if (userId != null) {
            USER_SESSIONS.remove(userId);
            ADMIN_SESSIONS.remove(userId);
            System.out.println("用户下线: " + userId);
        }
    }

    /**
     * 发送消息给指定用户
     */
    public void sendToUser(Integer userId, String message) {
        // 去大池子里找
        WebSocketSession session = USER_SESSIONS.get(userId);
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送消息给所有管理员
     */
    public void sendToAllAdmins(String message) {
        for (WebSocketSession session : ADMIN_SESSIONS.values()) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(message));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 提取通用发送逻辑，处理异常
    private void sendMessage(WebSocketSession session, String message) {
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
