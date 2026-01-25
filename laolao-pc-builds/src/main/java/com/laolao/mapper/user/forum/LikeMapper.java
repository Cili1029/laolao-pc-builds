package com.laolao.mapper.user.forum;

import com.laolao.pojo.forum.dto.LikeTarget;
import com.laolao.pojo.forum.entity.Like;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface LikeMapper {
    @Insert("INSERT INTO forum_like (user_id, like_type, like_id)" +
            "VALUES (#{userId}, #{likeType}, #{likeId})")
    void like(Like like);

    @Select("select status from forum_like where user_id = #{userId} and like_type = #{likeType} and like_id = #{likeId}")
    Integer getStatus(int userId, int likeType, int likeId);

    @Update("update forum_like set status = #{newStatus} where user_id = #{userId} and like_type = #{likeType} and like_id = #{likeId}")
    void updateStatus(int userId, int likeType, int likeId, int newStatus);
    
    List<Like> queryLike(@Param("userId") int currentId, @Param("targets") ArrayList<LikeTarget> likeTargets);

    @Select("select like_id from forum_like where status = 1 and like_type = 1 and user_id = #{id}")
    List<Integer> getLikePost(Integer id);
}
