package com.laolao.mapper.user.forum;

import com.laolao.pojo.forum.entity.Post;
import com.laolao.pojo.forum.vo.PostSimpleVO;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface PostMapper {
    @Select("select * from forum_post where category_id = #{categoryId} and status = 1 order by created_at desc")
    List<Post> selectPostSimple(int categoryId);

    @Select("select * from forum_post where id = #{id}")
    Post selectPost(int id);

    @Select("select * from forum_post where category_id = #{categoryId} and title like concat('%',#{searchContent},'%')")
    List<Post> searchPostSimple(int categoryId, String searchContent);

    @Insert("insert into forum_post(user_id, category_id, title, content, images, created_at, commented_by ,commented_at) " +
            "value (#{userId}, #{categoryId}, #{title}, #{content}, #{images}, #{createdAt}, #{commentedBy}, #{commentedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertPost(Post post);

    @Update("update forum_post set status = 0 where id = #{id}")
    void delete(int id);

    @Update("update forum_post set like_count = like_count + #{delta} where id = #{likeId}")
    void updateLikeCount(int likeId, int delta);

    void updateCommentCount(int id, int delta, LocalDateTime now, Integer commentedBy);

    @Select("select * from forum_post where user_id = #{id} and status = 1")
    List<Post> selectPostByUserid(Integer id);

    List<Post> getPostBatch(List<Integer> postIdList);

    @Select("""
            select id, category_id, title, like_count, comment_count, commented_by, commented_at
            from forum_post
            order by ((like_count * 1 + comment_count * 4) - TIMESTAMPDIFF(HOUR, created_at, NOW())) DESC
            """)
    List<PostSimpleVO> getHot();

    @Select("select user_id from forum_post where id = #{postId}")
    int selectUserByPost(int postId);
}
