package com.laolao.mapper.admin.shop;

import com.laolao.pojo.shop.entity.Variant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AdminVariantMapper {
    @Select("select * from shop_component_variant where component_id = #{id}")
    List<Variant> selectVariant(int id);

    @Update("update shop_component_variant set status = #{status} where component_id = #{id}")
    void updateVariantStatusByComponentId(int id, int status);

    @Update("update shop_component_variant set status = #{status} where id = #{id}")
    void updateVariantStatusByVariantId(int id, int status);
}
