package com.laolao.controller.user.shop;

import com.laolao.common.result.Result;
import com.laolao.pojo.shop.vo.ShopCouponVO;
import com.laolao.service.shop.ShopCouponService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 优惠券相关
 */
@RestController
@RequestMapping("/user/shop/coupon")
public class ShopCouponController {
    @Resource
    private ShopCouponService shopCouponService;

    /**
     * 获取已启用的优惠券
     *
     * @return 优惠券数据
     */
    @GetMapping
    public Result<List<ShopCouponVO>> getShopCoupon() {
        return shopCouponService.getShopCoupon();
    }
}
