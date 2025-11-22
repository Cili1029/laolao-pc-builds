package com.laolao.service.shop.impl;

import com.laolao.common.context.BaseContext;
import com.laolao.common.result.Result;
import com.laolao.converter.MapStruct;
import com.laolao.mapper.shop.ShopCouponMapper;
import com.laolao.pojo.shop.dto.GetCouponDTO;
import com.laolao.pojo.shop.entity.ShopCoupon;
import com.laolao.pojo.shop.vo.ShopCouponVO;
import com.laolao.pojo.shop.entity.UserCoupon;
import com.laolao.pojo.shop.vo.UserCouponVO;
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
    public Result<List<ShopCouponVO>> ShowShopCoupon() {
        List<ShopCoupon> couponList = shopCouponMapper.selectShopCoupon();
        List<ShopCouponVO> shopCouponVOS = new ArrayList<>();
        for (ShopCoupon coupon : couponList) {
            ShopCouponVO shopCouponVO = mapStruct.shopCouponToShopCouponVO(coupon);
            shopCouponVOS.add(shopCouponVO);
        }
        return Result.success(shopCouponVOS);
    }

    @Override
    public Result<List<UserCouponVO>> showUserCoupon() {
        List<UserCouponVO> couponList = shopCouponMapper.selectUserCoupon(BaseContext.getCurrentId(), 0);
        return Result.success(couponList);
    }

    @Override
    public Result<String> getCoupon(GetCouponDTO getCouponDTO) {
        int userId = BaseContext.getCurrentId();
        UserCoupon userCoupon = UserCoupon.builder()
                .userId(userId)
                .couponId(getCouponDTO.getCouponId())
                .build();
        shopCouponMapper.insertUserCoupon(userCoupon);
        return Result.success("领取成功！");
    }

    @Override
    public Result<List<UserCouponVO>> getAvailableCoupon(int userCouponId) {
        List<UserCouponVO> couponList = shopCouponMapper.selectAvailableCoupon(BaseContext.getCurrentId(), userCouponId);
        return Result.success(couponList);
    }
}
