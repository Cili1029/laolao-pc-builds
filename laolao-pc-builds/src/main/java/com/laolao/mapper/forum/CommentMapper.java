package com.laolao.mapper.forum;

import com.laolao.pojo.forum.entity.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Select("select * from forum_comment where post_id = #{id}")
    List<Comment> selectComment(int id);

    @Insert("insert into forum_comment(post_id, user_id, content, created_at) value (#{postId}, #{userId}, #{content}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertComment(Comment comment);

    @Insert("insert into forum_comment(post_id, user_id, parent, content, created_at) value (#{postId}, #{userId}, #{parent}, #{content}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertReply(Comment comment);

    @Update("update forum_comment set status = 2 where id = #{id} and user_id = #{userId}")
    int deleteComment(int id, int userId);

    @Update("update forum_comment set status = 2 where parent = #{id}")
    void deleteReplyByParent(int id);
}
