package com.laolao.mapper.admin.shop;

import com.laolao.pojo.shop.entity.ShopCategory;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminShopCategoryMapper {
    List<ShopCategory> select(int type);

    @Update("update shop_category set status = #{status}, updated_by = #{updatedBy}, updated_at = #{updatedAt} where id = #{id}")
    void updateStatus(ShopCategory shopCategory);

    void update(ShopCategory shopCategory);

    @Insert("insert into shop_category(product_type, name, image, created_by, created_at, updated_by, updated_at) " +
            "value (#{productType}, #{name}, #{image}, #{createdBy}, #{createdAt}, #{updatedBy}, #{updatedAt})")
    void insert(ShopCategory shopCategory);

    @Delete("delete from shop_category where id = #{id}")
    void delete(int id);
}
