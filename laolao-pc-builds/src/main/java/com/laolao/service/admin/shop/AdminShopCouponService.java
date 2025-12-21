package com.laolao.service.admin.shop;

import com.github.pagehelper.PageInfo;
import com.laolao.common.result.Result;
import com.laolao.pojo.shop.dto.AdminShopCouponDTO;
import com.laolao.pojo.shop.entity.ShopCoupon;


public interface AdminShopCouponService {
    Result<PageInfo<ShopCoupon>> getCoupon(Integer pageNum, Integer pageSize, String searchContent);

    Result<String> changeStatus(int id, int status);

    Result<String> update(AdminShopCouponDTO adminShopCouponDTO);

    Result<String> add(AdminShopCouponDTO adminShopCouponDTO);

    Result<String> delete(int id);
}
