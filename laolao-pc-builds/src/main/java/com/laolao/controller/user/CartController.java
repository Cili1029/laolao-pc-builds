package com.laolao.controller.user;

import com.laolao.common.result.Result;
import com.laolao.pojo.dto.CartProductDTO;
import com.laolao.service.CartService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 购物车相关
 */

@RestController
@RequestMapping("/user/cart")
public class CartController {

    @Resource
    private CartService cartService;

    /**
     * 添加商品进购物车
     *
     * @param cartProductDTO 商品类型和id
     * @return 结果消息
     */
    @PostMapping({"/add"})
    public Result<String> addToCart(@RequestBody CartProductDTO cartProductDTO) {
        return cartService.addToCart(cartProductDTO);
    }
}
