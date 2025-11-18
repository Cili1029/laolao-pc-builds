package com.laolao.service.shop;

import com.laolao.common.result.Result;
import com.laolao.pojo.shop.dto.GetCouponDTO;
import com.laolao.pojo.shop.vo.ShopCouponVO;
import com.laolao.pojo.shop.vo.UserCouponVO;

import java.util.List;

public interface ShopCouponService {
    Result<List<ShopCouponVO>> ShowShopCoupon();

    Result<List<UserCouponVO>> showUserCoupon();

    Result<String> getCoupon(GetCouponDTO getCouponDTO);
}
