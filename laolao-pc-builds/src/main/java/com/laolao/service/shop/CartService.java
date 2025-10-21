package com.laolao.service.shop;

import com.laolao.common.result.Result;
import com.laolao.pojo.shop.dto.CartProductDTO;
import com.laolao.pojo.shop.vo.CartProductVO;

import java.util.List;

public interface CartService {
    Result<String> addToCart(CartProductDTO cartProductDTO);

    Result<List<CartProductVO>> getCartProductList();

    Result<String> deleteFromCart(CartProductDTO cartProductDTO);

    Result<String> clearCart();
}
