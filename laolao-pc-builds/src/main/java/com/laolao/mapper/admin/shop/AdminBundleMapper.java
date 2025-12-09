package com.laolao.mapper.admin.shop;

import com.laolao.pojo.common.StockOrQuantityDTO;
import com.laolao.pojo.shop.dto.AdminBundleAddVariantDTO;
import com.laolao.pojo.shop.entity.Bundle;
import com.laolao.pojo.shop.vo.AdminBundleVariantVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminBundleMapper {
    @Select("select * from shop_bundle order by created_at desc")
    List<Bundle> selectBundle();

    @Update("update shop_bundle set status = #{status}, updated_by = #{updatedBy}, updated_at = #{updatedAt} where id = #{id}")
    void updateStatus(Bundle bundle);

    @Delete("delete from shop_bundle where id = #{id}")
    void delete(int id);

    @Insert("insert into shop_bundle(name, category_id, images, description, price, created_by, created_at, updated_by, updated_at) " +
            "value (#{name}, #{categoryId}, #{images}, #{description}, #{price} , #{createdBy}, #{createdAt}, #{updatedBy}, #{updatedAt})")
    void insert(Bundle bundle);

    @Update("update shop_bundle set name = #{name}, category_id = #{categoryId}, description = #{description}, price = #{price}, sort = #{sort}, images = #{images}, updated_by = #{updatedBy}, updated_at = #{updatedAt} where id = #{id}")
    void update(Bundle bundle);

    @Update("update shop_bundle set stock = #{stock}, updated_by = #{updatedBy}, updated_at = #{updatedAt} where id = #{id}")
    void updateStock(StockOrQuantityDTO stockOrQuantityDTO);

    @Select("""
            select b.id, v.id variantId, b.quantity, c.name, v.variant_name, v.price
            from shop_bundle_configuration b
            join shop_component_variant v on b.variant_id = v.id
            join shop_component c on v.component_id = c.id
            where b.bundle_id = #{id};
            """)
    List<AdminBundleVariantVO> getVariant(int id);

    @Update("update shop_bundle_configuration set quantity = #{quantity}, updated_by = #{updatedBy}, updated_at = #{updatedAt}  where id = #{id}")
    void updateQuantity(StockOrQuantityDTO stockOrQuantityDTO);

    @Delete("delete from shop_bundle_configuration where id = #{id}")
    void deleteVariant(int id);

    @Select("""
            select v.id variantId, c.name, v.variant_name, v.price
            from  shop_component_variant v join shop_component c on v.component_id = c.id
            where (c.name like CONCAT('%', #{searchContent}, '%') or c.id = #{searchContent} or v.id = #{searchContent}) and v.status = 1
            """)
    List<AdminBundleVariantVO> searchVariant(String searchContent);

    @Insert("insert into shop_bundle_configuration(bundle_id, variant_id, quantity, created_by, created_at, updated_by, updated_at) value (#{bundleId}, #{variantId}, #{quantity}, #{createdBy}, #{createdAt}, #{updatedBy}, #{updatedAt})")
    void insertVariant(AdminBundleAddVariantDTO adminBundleAddVariantDTO);
}
