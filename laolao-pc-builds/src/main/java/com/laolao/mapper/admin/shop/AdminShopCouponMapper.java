package com.laolao.mapper.admin.shop;

import com.laolao.pojo.shop.entity.ShopCoupon;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminShopCouponMapper {
    @Select("select * from shop_coupon order by status desc")
    List<ShopCoupon> select();

    @Update("update shop_coupon set status = #{status}, updated_by = #{updatedBy}, updated_at = #{updatedAt} where id = #{id}")
    void updateStatus(ShopCoupon shopCoupon);

    @Insert("insert into shop_coupon(name, description, minimum_amount, discount_amount, stock, valid_start_time, valid_end_time, created_by, created_at, updated_by, updated_at) " +
            "value (#{name}, #{description}, #{minimumAmount}, #{discountAmount}, #{stock}, #{validStartTime}, #{validEndTime}, #{createdBy}, #{createdAt}, #{updatedBy}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(ShopCoupon shopCoupon);

    @Delete("delete from shop_coupon where id = #{id}")
    void delete(int id);

    @Update("update shop_coupon set name = #{name}, description = #{description}, minimum_amount = #{minimumAmount}, " +
            "discount_amount = #{discountAmount}, stock = #{stock}, valid_start_time = #{validStartTime}, valid_end_time = #{validEndTime}, updated_by = #{updatedBy}, updated_at = #{updatedAt} " +
            "where id = #{id}")
    void update(ShopCoupon shopCoupon);

    @Select("select * from shop_coupon " +
            "where id like concat('%',#{searchContent},'%') or name like concat('%',#{searchContent},'%')" +
            "order by status desc")
    List<ShopCoupon> search(String searchContent);
}
