package com.laolao.controller.user.shop;

import com.laolao.common.result.Result;
import com.laolao.pojo.shop.dto.GetCouponDTO;
import com.laolao.pojo.shop.vo.ShopCouponVO;
import com.laolao.pojo.shop.vo.UserCouponVO;
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
     * 还有领取状态
     *
     * @return 优惠券数据
     */
    @GetMapping("/shop")
    public Result<List<ShopCouponVO>> showShopCoupon() {
        return shopCouponService.ShowShopCoupon();
    }

    /**
     * 获取拥护的优惠券
     * 只获取可以用的
     *
     * @return 优惠券数据
     */
    @GetMapping("/user")
    public Result<List<UserCouponVO>> showUserCoupon() {
        return shopCouponService.showUserCoupon();
    }

    /**
     * 用户获得优惠券
     *
     * @return 逐渐Id
     */
    @PostMapping("/get")
    public Result<String> getCoupon(@RequestBody GetCouponDTO getCouponDTO) {
        return shopCouponService.getCoupon(getCouponDTO);
    }

    /**
     * 获得订单可用优惠券
     *
     * @return 逐渐Id
     */
    @GetMapping("/order")
    public Result<List<UserCouponVO>> getAvailableCoupon(int userCouponId) {
        return shopCouponService.getAvailableCoupon(userCouponId);
    }
}
