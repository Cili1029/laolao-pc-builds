package com.laolao.mapper.admin.shop;

import com.laolao.pojo.shop.entity.Component;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AdminComponentMapper {
    @Select("select * from shop_component")
    List<Component> selectComponent();

    @Update("update shop_component set status = #{status} where id = #{id}")
    void updateComponentStatus(int id, int status);

    @Select("select c.status from shop_component c join shop_component_variant v on c.id = v.component_id where v.id = #{id}")
    int getStatusByVariantId(int id);
}
