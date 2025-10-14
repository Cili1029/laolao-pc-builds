package com.laolao.mapper;

import com.laolao.pojo.entity.CartItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CartMapper {
    CartItem isExists(CartItem cartItem);

    @Insert("insert into cart_items (user_id, type, product_id) VALUE (#{userId}, #{type}, #{productId})")
    void add(CartItem cartItem);

    @Update("update cart_items set quantity = quantity + #{delta} where user_id = #{cartItem.userId} and type = #{cartItem.type} and product_id = #{cartItem.productId}")
    void updateQuantity(CartItem cartItem, int delta);

    @Select("select * from cart_items where user_id = #{userId};")
    List<CartItem> getList(int userId);

    @Delete("delete from cart_items where user_id = #{userId} and type = #{type} and product_id = #{productId}")
    void del(CartItem cartItem);

    @Delete("delete from cart_items where user_id = #{userId}")
    void clear(int userId);
}
