package com.laolao.mapper.admin.shop;

import com.laolao.pojo.shop.entity.Component;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminComponentMapper {
    @Select("select * from shop_component order by created_at desc")
    List<Component> selectComponent();

    @Update("update shop_component set status = #{status}, updated_by = #{updatedBy}, updated_at = #{updatedAt} where id = #{id}")
    void updateComponentStatus(Component component);

    @Select("select c.status from shop_component c join shop_component_variant v on c.id = v.component_id where v.id = #{id}")
    int getStatusByVariantId(int id);

    @Delete("delete from shop_component where id = #{id}")
    void delete(int id);

    @Insert("insert into shop_component(name, category_id, images, common_description, created_by, created_at, updated_by, updated_at) value (#{name}, #{categoryId}, #{images}, #{commonDescription}, #{createdBy}, #{createdAt}, #{updatedBy}, #{updatedAt})")
    void insert(Component component);

    @Update("update shop_component set name = #{name}, category_id = #{categoryId}, common_description = #{commonDescription}, sort = #{sort}, images = #{images}, updated_by = #{updatedBy}, updated_at = #{updatedAt} where id = #{id}")
    void update(Component component);

    @Select("select * from shop_component " +
            "where id like concat('%',#{searchContent},'%') or name like concat('%',#{searchContent},'%') " +
            "order by created_at desc")
    List<Component> search(String searchContent);
}
