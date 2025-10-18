package com.laolao.mapper;

import com.laolao.pojo.entity.CartItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CartMapper {
    CartItem isExists(CartItem cartItem);

    @Insert("insert into cart_items (user_id, product_type, product_id) VALUE (#{userId}, #{productType}, #{productId})")
    void add(CartItem cartItem);

    @Update("update cart_items set quantity = quantity + #{delta} where user_id = #{cartItem.userId} and product_type = #{cartItem.productType} and product_id = #{cartItem.productId}")
    void updateQuantity(CartItem cartItem, int delta);

    @Select("select * from cart_items where user_id = #{userId};")
    List<CartItem> getList(int userId);

    @Delete("delete from cart_items where user_id = #{userId} and product_type = #{productType} and product_id = #{productId}")
    void del(CartItem cartItem);

    @Delete("delete from cart_items where user_id = #{userId}")
    void clear(int userId);

}
