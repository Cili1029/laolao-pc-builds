package com.laolao.mapper.shop;

import com.laolao.pojo.shop.entity.ShopCoupon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ShopCouponMapper {
    @Select("select * from shop_coupon where stock > 0 and status = 1")
    List<ShopCoupon> selectCoupon();
}
