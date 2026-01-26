package com.laolao.mapper.user.user;

import com.laolao.pojo.user.Listener.CommentNotification;
import com.laolao.pojo.user.Listener.ReplyNotification;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NotificationMapper {
    @Insert("insert into user_notification(receiver_id, sender_id, type, post_id, title, content, created_at) " +
            "value(#{receiverId}, #{senderId}, #{type}, #{postId}, #{title}, #{content}, #{createdAt})")
    void insertCommentNotification(CommentNotification notification);

    @Insert("insert into user_notification(receiver_id, sender_id, type, post_id, comment_id, title, content, created_at) " +
            "value(#{receiverId}, #{senderId}, #{type}, #{postId}, #{commentId}, #{title}, #{content}, #{createdAt})")
    void insertReplyNotification(ReplyNotification notification);
}
