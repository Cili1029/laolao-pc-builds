package com.laolao.mapper.forum;

import com.laolao.pojo.forum.entity.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PostMapper {
    @Select("select * from forum_post where category_id = #{categoryId}")
    List<Post> selectPost(int categoryId);
}
