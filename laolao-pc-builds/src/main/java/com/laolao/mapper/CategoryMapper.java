package com.laolao.mapper;

import com.laolao.pojo.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {
    @Select("select * from categories")
    List<Category> get();
}
