package com.laolao.mapper.admin.shop;

import com.laolao.pojo.shop.entity.Component;
import com.laolao.pojo.shop.entity.Variant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdminComponentMapper {
    @Select("select * from shop_component")
    List<Component> selectComponent();

    @Select("select * from shop_component_variant where component_id = #{id}")
    List<Variant> selectVariant(int id);
}
