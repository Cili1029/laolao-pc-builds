package com.laolao.mapper.user.user;

import com.laolao.pojo.user.entity.Notification;
import com.laolao.pojo.user.vo.NotificationVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface NotificationMapper {
    @Insert("insert into user_notification(receiver_id, sender_id, type, post_id, comment_id, title, content, created_at)" +
            "value (#{receiverId}, #{senderId}, #{type}, #{postId}, #{commentId}, #{title}, #{content}, #{createdAt})")
    void insertNotification(Notification notification);

    @Select("select count(id) from user_notification where receiver_id = #{userId} and status = 0")
    Integer selectUnReadCount(int userId);

    @Select("select n.id, type, post_id, n.title, n.content, n.created_at, p.category_id from user_notification n " +
            "left join forum_post p on n.post_id = p.id " +
            "where receiver_id = #{userId} order by created_at desc")
    List<NotificationVO> selectList(Integer userId);

    @Update("update user_notification set status = 1 where receiver_id = #{userId} and status = 0")
    void read(Integer userId);
}
