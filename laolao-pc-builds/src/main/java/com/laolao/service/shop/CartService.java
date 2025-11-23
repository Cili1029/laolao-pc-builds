package com.laolao.service.shop;

import com.laolao.common.result.Result;
import com.laolao.pojo.shop.dto.BuyProductDTO;
import com.laolao.pojo.shop.vo.CartProductVO;

import java.util.List;

public interface CartService {
    Result<String> addToCart(BuyProductDTO buyProductDTO);

    Result<List<CartProductVO>> getCartProductList();

    Result<String> deleteFromCart(BuyProductDTO buyProductDTO);

    Result<String> clearCart();
}
