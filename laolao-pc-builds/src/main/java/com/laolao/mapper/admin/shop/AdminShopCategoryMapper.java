package com.laolao.mapper.admin.shop;

import com.laolao.pojo.shop.dto.AdminShopCategoryDTO;
import com.laolao.pojo.shop.entity.ShopCategory;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminShopCategoryMapper {
    @Select("select * from shop_category")
    List<ShopCategory> select();

    @Update("update shop_category set status = #{status} where id = #{id}")
    void updateStatus(int id, int status);

    void update(AdminShopCategoryDTO adminShopCategoryDTO);

    @Insert("insert into shop_category(product_type, name, image, created_by) value (#{productType}, #{name}, #{image}, #{createdBy})")
    void insert(AdminShopCategoryDTO adminShopCategoryDTO);

    @Delete("delete from shop_category where id = #{id}")
    void delete(int id);
}
