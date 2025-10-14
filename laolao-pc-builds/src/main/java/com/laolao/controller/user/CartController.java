package com.laolao.controller.user;

import com.laolao.common.result.Result;
import com.laolao.pojo.dto.CartProductDTO;
import com.laolao.pojo.vo.CartProductVO;
import com.laolao.service.CartService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PostMapping("/plus")
    public Result<String> addToCart(@RequestBody CartProductDTO cartProductDTO) {
        return cartService.addToCart(cartProductDTO);
    }

    /**
     * 显示购物车清单
     *
     * @return 商品列表
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
     * @return 结果消息
     */
    @DeleteMapping("/minus")
    public Result<String> deleteFromCart(@RequestBody CartProductDTO cartProductDTO) {
        return cartService.deleteFromCart(cartProductDTO);
    }

    /**
     * 清空购物车
     *
     * @return 结果消息
     */
    @DeleteMapping("/clear")
    public Result<String> clearCart() {
        return cartService.clearCart();
    }
}
