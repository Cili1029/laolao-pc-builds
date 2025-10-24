package com.laolao.mapper.forum;

import com.laolao.pojo.forum.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Select("select * from forum_comment where post_id = #{id}")
    List<Comment> selectComment(int id);
}
