package com.laolao.mapper.admin.forum;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;


@Mapper
public interface AdminCommentMapper {

    @Delete("delete from forum_comment where post_id = #{id}")
    void deleteCommentByPostId(Integer id);

    @Update("update forum_comment set status = #{status} where post_id = #{postId}")
    void updateStatus(Integer postId,  Integer status);

    @Delete("delete from forum_comment where parent = #{id}")
    void deleteReplyByParentId(Integer id);

    @Delete("delete from forum_comment where id = #{id}")
    void deleteById(Integer id);
}
