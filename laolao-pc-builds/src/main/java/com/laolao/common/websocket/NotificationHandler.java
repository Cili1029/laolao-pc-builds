package com.laolao.common.websocket;

import com.laolao.common.constant.CommonConstant;
import com.laolao.common.result.WsMessage;
import com.laolao.mapper.dashboard.UserDashboardMapper;
import com.laolao.pojo.dashboard.vo.OnlineUserVO;
import com.laolao.pojo.dashboard.vo.UserDashboardVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class NotificationHandler extends TextWebSocketHandler {
    @Resource
    private UserDashboardMapper userDashboardMapper;

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
            if (admin == CommonConstant.UserType.ADMIN) {
                ADMIN_SESSIONS.put(userId, session);
            }
            broadcastOnlineCount();
        } else {
            session.close();
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        if ("ping".equals(message.getPayload())) {
            sendMessage(session, "pong");
        }
    }

    // 连接关闭
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        Integer userId = (Integer) session.getAttributes().get("userId");
        if (userId != null) {
            USER_SESSIONS.remove(userId);
            ADMIN_SESSIONS.remove(userId);
            broadcastOnlineCount();
        }
    }

    /**
     * 发送消息给指定用户
     */
    public void sendToUser(Integer userId, String message) {
        WebSocketSession session = USER_SESSIONS.get(userId);
        sendMessage(session, message);
    }

    /**
     * 发送消息给所有管理员
     */
    public void sendToAllAdmins(String message) {
        for (WebSocketSession session : ADMIN_SESSIONS.values()) {
            sendMessage(session, message);
        }
    }

    // 提取通用发送逻辑，处理异常
    private void sendMessage(WebSocketSession session, String message) {
        if (session != null && session.isOpen()) {
            // 关键点：对 session 对象加锁
            // 保证同一时刻，只有一个线程能往这个 session 写数据
            synchronized (session) {
                try {
                    if (session.isOpen()) { // 双重检查
                        session.sendMessage(new TextMessage(message));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 广播在线人数更新
     */
    private void broadcastOnlineCount() {
        // 获取当前人数
        int totalCount = USER_SESSIONS.size();
        // 获取前5个用户的 ID
        List<Integer> ids = USER_SESSIONS.keySet().stream()
                .limit(4)
                .toList();
        List<UserDashboardVO> onlineUserList;
        if (ids.isEmpty()) {
            onlineUserList = new ArrayList<>(); // 没人在线，给空列表
        } else {
            onlineUserList = userDashboardMapper.selectOnlineUser(ids);
        }
        // 组装 VO
        OnlineUserVO onlineUserVO = new OnlineUserVO();
        onlineUserVO.setOnlineCount(totalCount);
        onlineUserVO.setOnlineUsers(onlineUserList);
        String jsonMessage = WsMessage.of("online_users", onlineUserVO);
        sendToAllAdmins(jsonMessage);
    }
}
