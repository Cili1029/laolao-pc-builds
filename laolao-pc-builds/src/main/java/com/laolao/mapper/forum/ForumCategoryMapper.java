package com.laolao.mapper.forum;

import com.laolao.pojo.forum.entity.ForumCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ForumCategoryMapper {
    @Select("select * from forum_category")
    List<ForumCategory> get();
}
