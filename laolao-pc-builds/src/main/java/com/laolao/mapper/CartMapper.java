package com.laolao.mapper;

import com.laolao.pojo.dto.CartProductDTO;
import com.laolao.pojo.entity.CartItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CartMapper {
    @Select("select * from cart_items where user_id = #{userId} and type = #{param2.type} and product_id = #{param2.productId}")
    CartItem isExists(int userId,CartProductDTO cartProductDTO);

    @Insert("insert into cart_items (user_id, type, product_id) VALUE (#{userId}, #{param2.type}, #{param2.productId})")
    void add(int userId, CartProductDTO cartProductDTO);

    @Update("update cart_items set quantity = quantity + 1 where user_id = #{userId} and type = #{param2.type} and product_id = #{param2.productId}")
    void update(int userId, CartProductDTO cartProductDTO);
}
