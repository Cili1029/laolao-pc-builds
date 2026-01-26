package com.laolao.service.user.user.impl;

import com.laolao.common.constant.NotificationConstant;
import com.laolao.mapper.user.forum.CommentMapper;
import com.laolao.mapper.user.forum.PostMapper;
import com.laolao.mapper.user.user.NotificationMapper;
import com.laolao.pojo.user.Listener.CommentNotification;
import com.laolao.pojo.user.Listener.ReplyNotification;
import com.laolao.service.user.user.NotificationService;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Resource
    private PostMapper postMapper;
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private NotificationMapper notificationMapper;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void commentNotification(CommentNotification notification) {
        // 查询发帖用户
        int userId = postMapper.selectUserByPost(notification.getPostId());
        // 自己回复自己
        if (userId == notification.getSenderId()) {
            return;
        }
        notification.setReceiverId(userId);
        notification.setType(NotificationConstant.COMMENT);
        notification.setTitle("新评论");
        // 截取内容防溢出
        notification.setContent(notification.getContent().length() > 9 ? notification.getContent().substring(0, 8) : notification.getContent() + "...");
        // 写入数据库
        notificationMapper.insertCommentNotification(notification);
        // TODO websocket
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void replyNotification(ReplyNotification notification) {
        // 查询发父评论的用户
        int userId = commentMapper.selectUserByPost(notification.getCommentId());
        // 自己回复自己
        if (userId == notification.getSenderId()) {
            return;
        }
        notification.setReceiverId(userId);
        notification.setType(NotificationConstant.REPLY);
        notification.setTitle("新回复");
        // 截取内容防溢出
        notification.setContent(notification.getContent().length() > 9 ? notification.getContent().substring(0, 8) : notification.getContent() + "...");
        // 写入数据库
        notificationMapper.insertReplyNotification(notification);
        // TODO websocket
    }
}
