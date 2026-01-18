package com.laolao.mapper.user.shop;

import com.laolao.pojo.shop.dto.CouponDTO;
import com.laolao.pojo.shop.entity.ShopCoupon;
import com.laolao.pojo.shop.entity.UserCoupon;
import com.laolao.pojo.shop.vo.UserCouponVO;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface ShopCouponMapper {
    @Select("SELECT s.*, IF(u.coupon_id IS NOT NULL, 1, 0) AS obtained " +
            "FROM shop_coupon s LEFT JOIN user_coupon u ON s.id = u.coupon_id AND u.user_id = #{userId} " +
            "WHERE s.stock > 0 AND s.status = 1;")
    List<ShopCoupon> selectShopCoupon(int userId);

    List<UserCouponVO> selectUserCoupon(int userId, int status);

    @Insert("insert into user_coupon(user_id, coupon_id) value (#{userId}, #{couponId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertUserCoupon(UserCoupon userCoupon);

    @Update("update user_coupon set order_id = #{number}, status = #{status}, used_at = #{usedAt} where id = #{id} and user_id = #{userId}")
    void UpdateCoupon(CouponDTO couponDTO);

    @Select("select discount_amount from shop_coupon s join user_coupon u on s.id = u.coupon_id where u.id = #{id}")
    BigDecimal selectShopCouponById(int id);

    @Select("update user_coupon set order_id = null, status = 0, used_at = null where order_id = #{number}")
    void cancelUseCoupon(String number);

    @Select("select * from user_coupon u join shop_coupon s on u.coupon_id = s.id where user_id = #{userId} or u.id = #{userCouponId}")
    List<UserCouponVO> selectAvailableCoupon(int userId, int userCouponId);

    @Select("select count(id) from user_coupon where user_id = #{userId} and coupon_id = #{couponId} ")
    int isGotTheCoupon(int userId, int couponId);

    @Update("update shop_coupon set stock = stock - 1 where id = #{couponId}")
    void updateStock(int couponId);
}
