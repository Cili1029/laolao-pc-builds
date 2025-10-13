package com.laolao.service;

import com.laolao.common.result.Result;
import com.laolao.pojo.dto.CartProductDTO;

public interface CartService {
    Result<String> addToCart(CartProductDTO cartProductDTO);
}
