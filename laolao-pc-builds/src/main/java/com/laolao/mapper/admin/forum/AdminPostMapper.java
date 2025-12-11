package com.laolao.mapper.admin.forum;

import com.laolao.pojo.forum.entity.Post;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdminPostMapper {
    @Select("select * from forum_post order by created_at desc")
    List<Post> select();

    void update(Post post);

    @Delete("delete from forum_post where id = #{id}")
    void delete(Integer id);

}
