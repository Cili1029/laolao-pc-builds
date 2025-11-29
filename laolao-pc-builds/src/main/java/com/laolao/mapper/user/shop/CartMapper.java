package com.laolao.mapper.user.shop;

import com.laolao.pojo.shop.entity.CartItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CartMapper {
    CartItem isExists(CartItem cartItem);

    @Insert("insert into shop_cart_item (user_id, product_type, product_id, quantity) VALUE (#{userId}, #{productType}, #{productId}, #{quantity})")
    void add(CartItem cartItem);

    @Update("update shop_cart_item set quantity = quantity + #{delta} where user_id = #{cartItem.userId} and product_type = #{cartItem.productType} and product_id = #{cartItem.productId}")
    void updateQuantity(CartItem cartItem, int delta);

    @Select("select * from shop_cart_item where user_id = #{userId};")
    List<CartItem> getList(int userId);

    @Delete("delete from shop_cart_item where user_id = #{userId} and product_type = #{productType} and product_id = #{productId}")
    void del(CartItem cartItem);

    @Delete("delete from shop_cart_item where user_id = #{userId}")
    void clear(int userId);

}
