package com.laolao.mapper.shop;

import com.laolao.pojo.shop.dto.CouponDTO;
import com.laolao.pojo.shop.entity.ShopCoupon;
import com.laolao.pojo.shop.entity.UserCoupon;
import com.laolao.pojo.shop.vo.UserCouponVO;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface ShopCouponMapper {
    @Select("select * from shop_coupon where stock > 0 and status = 1")
    List<ShopCoupon> selectShopCoupon();

    List<UserCouponVO> selectUserCoupon(int id, int userId);

    @Insert("insert into user_coupon(user_id, coupon_id) value (#{userId}, #{couponId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertUserCoupon(UserCoupon userCoupon);

    @Update("update user_coupon set order_id = #{number}, status = #{status}, used_at = #{usedAt} where id = #{id} and user_id = #{userId}")
    void UpdateCoupon(CouponDTO couponDTO);

    @Select("select discount_amount from shop_coupon s join user_coupon u on s.id = u.coupon_id where u.id = #{id}")
    BigDecimal selectShopCouponById(int id);
}
