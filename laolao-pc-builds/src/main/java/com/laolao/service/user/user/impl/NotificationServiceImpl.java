package com.laolao.service.user.user.impl;

import com.laolao.common.constant.ForumLikeConstant;
import com.laolao.common.constant.NotificationConstant;
import com.laolao.common.constant.OrderConstant;
import com.laolao.common.context.UserContext;
import com.laolao.common.result.Result;
import com.laolao.converter.MapStruct;
import com.laolao.mapper.user.forum.CommentMapper;
import com.laolao.mapper.user.forum.PostMapper;
import com.laolao.mapper.user.shop.OrderMapper;
import com.laolao.mapper.user.user.NotificationMapper;
import com.laolao.pojo.user.Listener.*;
import com.laolao.pojo.user.entity.Notification;
import com.laolao.pojo.user.vo.NotificationVO;
import com.laolao.service.user.user.NotificationService;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;


@Service
public class NotificationServiceImpl implements NotificationService {
    @Resource
    private PostMapper postMapper;
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private NotificationMapper notificationMapper;
    @Resource
    private MapStruct mapStruct;
    @Resource
    private OrderMapper orderMapper;

    @Override
    public Result<Integer> getReadStatus() {
        int userId = UserContext.getCurrentId();
        Integer count = notificationMapper.selectUnReadCount(userId);
        return Result.success(count);
    }

    @Override
    public Result<List<NotificationVO>> getList() {
        Integer userId = UserContext.getCurrentId();
        // 查询
        List<NotificationVO> notificationVO = notificationMapper.selectList(userId);
        // 更新为已读
        notificationMapper.read(userId);
        return Result.success(notificationVO);
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Override
    public void commentNotification(CommentNotification commentNotification) {
        // 查询发帖用户和标题
        int userId = postMapper.selectUserByPost(commentNotification.getPostId());
        // 自己回复自己
        if (userId == commentNotification.getSenderId()) {
            return;
        }
        commentNotification.setReceiverId(userId);
        commentNotification.setType(NotificationConstant.COMMENT);
        commentNotification.setTitle("新评论");
        // 截取内容防溢出
        commentNotification.setContent("有人评论：" + (commentNotification.getContent().length() > 9 ?
                (commentNotification.getContent().substring(0, 8) + "...") : commentNotification.getContent()));
        // 转换写入数据库
        Notification notification = mapStruct.commentNotificationToNotification(commentNotification);
        notificationMapper.insertNotification(notification);
        // TODO websocket
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Override
    public void replyNotification(ReplyNotification replyNotification) {
        // 查询发父评论的用户
        int userId = commentMapper.selectUserByComment(replyNotification.getCommentId());
        // 自己回复自己
        if (userId == replyNotification.getSenderId()) {
            return;
        }
        replyNotification.setReceiverId(userId);
        replyNotification.setType(NotificationConstant.REPLY);
        replyNotification.setTitle("新回复");
        // 截取内容防溢出
        replyNotification.setContent("有人回复你：" + (replyNotification.getContent().length() > 9 ?
                (replyNotification.getContent().substring(0, 8) + "...") : replyNotification.getContent()));
        // 转换写入数据库
        Notification notification = mapStruct.replyNotificationToNotification(replyNotification);
        notificationMapper.insertNotification(notification);
        // TODO websocket
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Override
    public void likeNotification(LikeNotification likeNotification) {
        // 定义类型和目标
        if (likeNotification.getLikeType() == ForumLikeConstant.POST) {
            // 帖子点赞
            likeNotification.setType(NotificationConstant.POST_LIKE);
            likeNotification.setPostId(likeNotification.getLikeId());
            // 查询发帖用户和标题
            LikePostOrComment post = postMapper.selectUserAndTitleByPost(likeNotification.getPostId());
            // 自己回复自己
            if (post.getUserId() == likeNotification.getSenderId()) {
                return;
            }
            likeNotification.setReceiverId(post.getUserId());
            likeNotification.setTitle("新点赞");
            likeNotification.setContent("有人点赞了你的帖子： " + (post.getContent().length() > 9 ?
                    (post.getContent().substring(0, 8) + "...") : post.getContent()));
            // 转换写入
            Notification notification = mapStruct.likeNotificationToNotification(likeNotification);
            // 写入数据库
            notificationMapper.insertNotification(notification);
        } else {
            // 评论点赞
            likeNotification.setCommentId(likeNotification.getLikeId());
            likeNotification.setType(NotificationConstant.COMMENT_LIKE);

            // 查询发父评论的用户和回复标题
            LikePostOrComment comment = commentMapper.selectUserAndTitleByComment(likeNotification.getLikeId());
            // 自己回复自己
            if (comment.getUserId() == likeNotification.getSenderId()) {
                return;
            }
            likeNotification.setReceiverId(comment.getUserId());
            likeNotification.setPostId(comment.getPostId());
            likeNotification.setTitle("新点赞");
            // 截取内容防溢出
            likeNotification.setContent("有人点赞了你的评论: " + (comment.getContent().length() > 9 ?
                    (comment.getContent().substring(0, 8) + "...") : comment.getContent()));
            // 转换写入数据库
            Notification notification = mapStruct.likeNotificationToNotification(likeNotification);
            notificationMapper.insertNotification(notification);
        }
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Override
    public void ShippedNotification(OrderNotification orderNotification) {
        // 查询接收者和订单号

        OrderNumberAndUser numberAndUser = orderMapper.selectUserByNumber(orderNotification.getOrderId());
        orderNotification.setReceiverId(numberAndUser.getUserId());
        orderNotification.setTitle("发货通知");
        orderNotification.setContent("订单 " + numberAndUser.getNumber() + (orderNotification.getOrderStatus() == OrderConstant.SHIPPED ? " 发货" : " 到货") + "了");
        // 转换写入数据库
        Notification notification = mapStruct.orderNotificationToNotification(orderNotification);
        notificationMapper.insertNotification(notification);
    }
}
