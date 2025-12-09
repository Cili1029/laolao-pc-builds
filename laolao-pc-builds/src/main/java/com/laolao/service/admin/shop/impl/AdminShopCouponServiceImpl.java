package com.laolao.service.admin.shop.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.laolao.common.result.Result;
import com.laolao.converter.MapStruct;
import com.laolao.mapper.admin.shop.AdminShopCouponMapper;
import com.laolao.pojo.shop.dto.AdminShopCouponDTO;
import com.laolao.pojo.shop.entity.ShopCoupon;
import com.laolao.service.admin.shop.AdminShopCouponService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminShopCouponServiceImpl implements AdminShopCouponService {

    @Resource
    private AdminShopCouponMapper adminShopCouponMapper;
    @Resource
    private MapStruct mapStruct;

    @Override
    public Result<PageInfo<ShopCoupon>> getCoupon(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ShopCoupon> shopCouponList =  adminShopCouponMapper.select();
        PageInfo<ShopCoupon> res = new PageInfo<>(shopCouponList);
        return Result.success(res);
    }

    @Override
    public Result<String> changeStatus(int id, int status) {
        ShopCoupon shopCoupon = new ShopCoupon();
        shopCoupon.setId(id);
        shopCoupon.setStatus(status);
        adminShopCouponMapper.updateStatus(shopCoupon);
        return Result.success(status == 1 ? "已启用！" : "已禁用！");
    }

    @Override
    public Result<String> update(AdminShopCouponDTO adminShopCouponDTO) {
        ShopCoupon shopCoupon = mapStruct.adminShopCouponDTOToShopCoupon(adminShopCouponDTO);
        adminShopCouponMapper.update(shopCoupon);
        return Result.success("修改成功");
    }

    @Override
    public Result<String> add(AdminShopCouponDTO adminShopCouponDTO) {
        ShopCoupon shopCoupon = mapStruct.adminShopCouponDTOToShopCoupon(adminShopCouponDTO);
        adminShopCouponMapper.insert(shopCoupon);
        return Result.success("新增成功");
    }

    @Override
    public Result<String> delete(int id) {
        adminShopCouponMapper.delete(id);
        return Result.success("删除成功");
    }
}
