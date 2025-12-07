package com.laolao.mapper.admin.shop;

import com.laolao.pojo.shop.dto.AdminShopCouponDTO;
import com.laolao.pojo.shop.entity.ShopCoupon;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminShopCouponMapper {
    @Select("select * from shop_coupon order by status desc")
    List<ShopCoupon> select();

    @Update("update shop_coupon set status = #{status} where id = #{id}")
    void updateStatus(int id, int status);

    @Insert("insert into shop_coupon(name, description, minimum_amount, discount_amount, stock, valid_start_time, valid_end_time) " +
            "value (#{name}, #{description}, #{minimumAmount}, #{discountAmount}, #{stock}, #{validStartTime}, #{validEndTime})")
    void insert(AdminShopCouponDTO adminShopCouponDTO);

    @Delete("delete from shop_coupon where id = #{id}")
    void delete(int id);

    @Update("update shop_coupon set name = #{name}, description = #{description}, minimum_amount = #{minimumAmount}, " +
            "discount_amount = #{discountAmount}, stock = #{stock}, valid_start_time = #{validStartTime}, valid_end_time = #{validEndTime} " +
            "where id = #{id}")
    void update(AdminShopCouponDTO adminShopCouponDTO);
}
