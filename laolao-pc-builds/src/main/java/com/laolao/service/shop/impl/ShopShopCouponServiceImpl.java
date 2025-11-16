package com.laolao.service.shop.impl;

import com.laolao.common.result.Result;
import com.laolao.converter.MapStruct;
import com.laolao.mapper.shop.ShopCouponMapper;
import com.laolao.pojo.shop.entity.ShopCoupon;
import com.laolao.pojo.shop.vo.ShopCouponVO;
import com.laolao.service.shop.ShopCouponService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopShopCouponServiceImpl implements ShopCouponService {
    @Resource
    private ShopCouponMapper shopCouponMapper;
    @Resource
    private MapStruct mapStruct;

    @Override
    public Result<List<ShopCouponVO>> getShopCoupon() {
        List<ShopCoupon> couponList = shopCouponMapper.selectCoupon();
        List<ShopCouponVO> shopCouponVOS = new ArrayList<>();
        for (ShopCoupon coupon : couponList) {
            ShopCouponVO shopCouponVO = mapStruct.shopCouponToShopCouponVO(coupon);
            shopCouponVOS.add(shopCouponVO);
        }
        return Result.success(shopCouponVOS);
    }
}
