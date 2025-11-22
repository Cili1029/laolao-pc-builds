package com.laolao.mapper.shop;

import com.laolao.pojo.shop.entity.Component;
import com.laolao.pojo.shop.entity.Variant;
import com.laolao.pojo.shop.vo.CartProductVO;
import com.laolao.pojo.shop.vo.IdAndQuantityVO;
import com.laolao.pojo.shop.vo.ProductVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ComponentMapper {
    List<ProductVO> getByConditions(int categoryId, String searchContent);

    @Select("select product_type from shop_category where id = #{categoryId}")
    int getType(int categoryId);

    @Select("select * from shop_component_variant where id = #{id} and status = 1 and stock > 0")
    Variant check(int id);

    List<CartProductVO> ListFromCart(int userId, List<Integer> productIds);

    void updateStock(List<IdAndQuantityVO> components);

    @Select("select * from shop_component where id = #{id}")
    Component getProduct(int id);

    @Select("select * from shop_component_variant where component_id = #{id}")
    List<Variant> selectVariants(int id);
}
