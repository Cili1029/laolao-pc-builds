package com.laolao.mapper.shop;

import com.laolao.pojo.shop.entity.Bundle;
import com.laolao.pojo.shop.entity.Variant;
import com.laolao.pojo.shop.vo.CartProductVO;
import com.laolao.pojo.shop.vo.IdAndQuantityVO;
import com.laolao.pojo.shop.vo.VariantVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BundleMapper {

    List<Bundle> getByConditions(int categoryId, String searchContent);

    @Select("select * from shop_bundle where id = 1 and status = 1")
    Bundle check(int id);

    List<CartProductVO> ListFromCart(int userId, List<Integer> productIds);

    void updateStock(List<IdAndQuantityVO> bundles);

    @Select("select * from shop_bundle where id = #{id}")
    Bundle selectBundle(int id);

    @Select("select c.*, b.quantity from shop_bundle_configuration b join shop_component_variant c on b.variant_id = c.id " +
            "in (select variant_id from shop_bundle_configuration where bundle_id = #{id})")
    List<Variant> selectVariants(int id);

    @Select("select cv.*, co.name, co.image, b.quantity from shop_bundle_configuration b " +
            "join shop_component_variant cv on b.variant_id = cv.id " +
            "join shop_component co on co.id = cv.component_id " +
            "where cv.id in (select variant_id from shop_bundle_configuration where bundle_id = #{id})")
    List<VariantVO> selectBundleVariants(int id);
}
