package com.laolao.mapper.admin.forum;

import com.laolao.pojo.forum.entity.ForumCategory;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminForumCategoryMapper {
    @Select("select * from forum_category")
    List<ForumCategory> select();

    @Update("update forum_category set status = #{status}, updated_by = #{updatedBy}, updated_at = #{updatedAt} where id = #{id}")
    void updateStatus(ForumCategory forumCategory);

    void update(ForumCategory forumCategory);

    @Insert("insert into forum_category(name, description ,image, created_by, created_at, updated_by, updated_at) " +
            "value (#{name}, #{description}, #{image}, #{createdBy}, #{createdAt}, #{updatedBy}, #{updatedAt})")
    void insert(ForumCategory forumCategory);

    @Delete("delete from forum_category where id = #{id}")
    void delete(int id);
}
