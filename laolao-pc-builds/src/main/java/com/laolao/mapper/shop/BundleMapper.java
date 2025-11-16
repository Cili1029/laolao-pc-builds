package com.laolao.mapper.shop;

import com.laolao.pojo.shop.entity.Bundle;
import com.laolao.pojo.shop.vo.CartProductVO;
import com.laolao.pojo.shop.vo.ComponentVariantVO;
import com.laolao.pojo.shop.vo.IdAndQuantityVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BundleMapper {

    List<Bundle> getByConditions(int categoryId, String searchContent);

    @Select("select cv.id, c.name, cv.variant_name, c.image ,cv.price " +
            "from shop_component_variant cv join shop_component c on cv.component_id = c.id where cv.id in " +
            "(select variant_id from shop_bundle_configuration b where bundle_id = #{id})")
    List<ComponentVariantVO> getByComponentsId(int id);

    @Select("select * from shop_bundle where id = 1 and status = 1")
    Bundle check(int id);

    List<CartProductVO> ListFromCart(int userId, List<Integer> productIds);

    void updateStock(List<IdAndQuantityVO> bundles);
}
