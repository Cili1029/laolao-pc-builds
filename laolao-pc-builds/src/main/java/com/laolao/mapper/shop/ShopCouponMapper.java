package com.laolao.mapper.shop;

import com.laolao.pojo.shop.entity.ShopCoupon;
import com.laolao.pojo.shop.entity.UserCoupon;
import com.laolao.pojo.shop.vo.UserCouponVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ShopCouponMapper {
    @Select("select * from shop_coupon where stock > 0 and status = 1")
    List<ShopCoupon> selectShopCoupon();

    List<UserCouponVO> selectUserCoupon(int id, int userId);

    @Insert("insert into user_coupon(user_id, coupon_id) value (#{userId}, #{couponId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertUserCoupon(UserCoupon userCoupon);
}
