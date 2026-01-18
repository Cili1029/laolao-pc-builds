package com.laolao.service.admin.shop.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.laolao.common.constant.RedisConstant;
import com.laolao.common.result.Result;
import com.laolao.converter.MapStruct;
import com.laolao.mapper.admin.shop.AdminShopCouponMapper;
import com.laolao.pojo.shop.dto.AdminShopCouponDTO;
import com.laolao.pojo.shop.entity.ShopCoupon;
import com.laolao.service.admin.shop.AdminShopCouponService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminShopCouponServiceImpl implements AdminShopCouponService {

    @Resource
    private AdminShopCouponMapper adminShopCouponMapper;
    @Resource
    private MapStruct mapStruct;
    @Resource
    private RedissonClient redissonClient;

    @Override
    public Result<PageInfo<ShopCoupon>> getCoupon(Integer pageNum, Integer pageSize, String searchContent) {
        PageHelper.startPage(pageNum, pageSize);
        List<ShopCoupon> shopCouponList = StringUtils.isNoneBlank(searchContent)
                ? adminShopCouponMapper.search(searchContent)
                : adminShopCouponMapper.select();
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
        RSemaphore semaphore = redissonClient.getSemaphore(RedisConstant.Shop.COUPON_STOCK + adminShopCouponDTO.getId());
        semaphore.delete();
        semaphore.trySetPermits(adminShopCouponDTO.getStock());
        return Result.success("修改成功");
    }

    @Override
    public Result<String> add(AdminShopCouponDTO adminShopCouponDTO) {
        ShopCoupon shopCoupon = mapStruct.adminShopCouponDTOToShopCoupon(adminShopCouponDTO);
        adminShopCouponMapper.insert(shopCoupon);
        redissonClient.getSemaphore(RedisConstant.Shop.COUPON_STOCK + shopCoupon.getId()).trySetPermits(shopCoupon.getStock());
        return Result.success("新增成功");
    }

    @Override
    public Result<String> delete(int id) {
        adminShopCouponMapper.delete(id);
        redissonClient.getSemaphore(RedisConstant.Shop.COUPON_STOCK + id).delete();
        return Result.success("删除成功");
    }
}
