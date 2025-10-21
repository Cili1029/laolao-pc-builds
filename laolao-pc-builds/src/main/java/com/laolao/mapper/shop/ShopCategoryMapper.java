package com.laolao.mapper.shop;

import com.laolao.pojo.shop.entity.ShopCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ShopCategoryMapper {
    @Select("select * from shop_category")
    List<ShopCategory> get();
}
