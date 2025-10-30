package com.laolao.mapper.forum;

import com.laolao.pojo.forum.entity.Post;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PostMapper {
    @Select("select * from forum_post where category_id = #{categoryId} and status = 1 order by created_at desc")
    List<Post> selectPostSimple(int categoryId);

    @Select("select * from forum_post where id = #{id}")
    Post selectPost(int id);

    @Select("select * from forum_post where category_id = #{categoryId} and title like concat('%',#{searchContent},'%')")
    List<Post> searchPostSimple(int categoryId, String searchContent);

    @Insert("insert into forum_post(user_id, category_id, title, content, images, created_at) value (#{userId}, #{categoryId}, #{title}, #{content}, #{images}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertPost(Post post);

    @Update("update forum_post set status = 2 where id = #{id} and user_id = #{userId}")
    void delete(int id, int userId);

    @Update("update forum_post set like_count = like_count + #{delta} where id = #{likeId}")
    void updateLikeCount(int likeId, int delta);
}
