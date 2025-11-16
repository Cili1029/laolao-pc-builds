package com.laolao.service.shop;

import com.laolao.common.result.Result;
import com.laolao.pojo.shop.vo.ShopCouponVO;

import java.util.List;

public interface ShopCouponService {
    Result<List<ShopCouponVO>> getShopCoupon();
}
