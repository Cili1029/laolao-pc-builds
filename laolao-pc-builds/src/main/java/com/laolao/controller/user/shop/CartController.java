package com.laolao.controller.user.shop;

import com.laolao.common.result.Result;
import com.laolao.pojo.shop.dto.CartProductDTO;
import com.laolao.pojo.shop.vo.CartProductVO;
import com.laolao.service.shop.CartService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 购物车相关
 */
@RestController
@RequestMapping("/user/shop/cart")
public class CartController {

    @Resource
    private CartService cartService;

    /**
     * 添加商品到购物车
     *
     * @param cartProductDTO 商品类型和id
     * @return 结果信息
     */
    @PostMapping("/plus")
    public Result<String> addToCart(@RequestBody CartProductDTO cartProductDTO) {
        return cartService.addToCart(cartProductDTO);
    }

    /**
     * 获取购物车中的商品
     *
     * @return 商品数据
     */
    @GetMapping("/list")
    public Result<List<CartProductVO>> getCartProductList() {
        return cartService.getCartProductList();
    }


    /**
     * 删除购物车中的商品
     * 大于1则-1，等于1则删除
     *
     * @param cartProductDTO 商品类型和id
     * @return 结果信息
     */
    @DeleteMapping("/minus")
    public Result<String> deleteFromCart(@RequestBody CartProductDTO cartProductDTO) {
        return cartService.deleteFromCart(cartProductDTO);
    }

    /**
     * 清空购物车
     *
     * @return 结果信息
     */
    @DeleteMapping("/clear")
    public Result<String> clearCart() {
        return cartService.clearCart();
    }
}
