package com.laolao.mapper.user.forum;

import com.laolao.pojo.forum.entity.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Select("SELECT fc1.*, (SELECT COUNT(*) FROM forum_comment fc2 WHERE fc2.parent = fc1.id AND fc2.status = 1) as reply_count FROM forum_comment fc1 WHERE fc1.post_id = #{id} AND fc1.status = 1 AND fc1.parent = 0;")
    List<Comment> selectComment(int id);

    @Select("select * from forum_comment where parent = #{id} and status = 1")
    List<Comment> selectReply(int id);

    @Insert("insert into forum_comment(post_id, user_id, content, images, created_at) value (#{postId}, #{userId}, #{content}, #{images}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertComment(Comment comment);

    @Insert("insert into forum_comment(post_id, user_id, parent, content, images, created_at) value (#{postId}, #{userId}, #{parent}, #{content}, #{images}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertReply(Comment comment);

    @Update("update forum_comment set status = 2 where id = #{id} and user_id = #{userId}")
    int deleteComment(int id, int userId);

    @Update("update forum_comment set status = 2 where parent = #{id}")
    int deleteReplyByParent(int id);

    @Update("update forum_comment set status = 2 where post_id = #{id} and user_id = #{userId}")
    void deleteCommentByPostId(int id, int userId);

    @Update("update forum_comment set like_count = like_count + #{delta} where id = #{likeId}")
    void updateLikeCount(int likeId, int delta);
}
