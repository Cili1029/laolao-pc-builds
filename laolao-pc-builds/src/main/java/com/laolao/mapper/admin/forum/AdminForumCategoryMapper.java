package com.laolao.mapper.admin.forum;

import com.laolao.pojo.forum.dto.AdminForumCategoryDTO;
import com.laolao.pojo.forum.entity.ForumCategory;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminForumCategoryMapper {
    @Select("select * from forum_category")
    List<ForumCategory> select();

    @Update("update forum_category set status = #{status} where id = #{id}")
    void updateStatus(int id, int status);

    void update(AdminForumCategoryDTO adminForumCategoryDTO);

    @Insert("insert into forum_category(name, description ,image, created_by) value (#{name}, #{description}, #{image}, #{createdBy})")
    void insert(AdminForumCategoryDTO adminForumCategoryDTO);

    @Delete("delete from forum_category where id = #{id}")
    void delete(int id);
}
