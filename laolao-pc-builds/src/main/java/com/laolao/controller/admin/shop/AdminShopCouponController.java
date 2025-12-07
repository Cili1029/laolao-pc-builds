package com.laolao.controller.admin.shop;

import com.github.pagehelper.PageInfo;
import com.laolao.common.result.Result;
import com.laolao.pojo.common.StatusDTO;
import com.laolao.pojo.shop.dto.AdminShopCouponDTO;
import com.laolao.pojo.shop.dto.AdminShopCategoryDTO;
import com.laolao.pojo.shop.entity.ShopCoupon;
import com.laolao.service.admin.shop.AdminShopCouponService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;


/**
 * 管理端优惠券相关
 */
@RestController
@RequestMapping("/admin/shop/coupon")
public class AdminShopCouponController {
    @Resource
    private AdminShopCouponService adminCouponService;

    /**
     * 获取优惠券
     *
     * @return 优惠券数据
     */
    @GetMapping
    public Result<PageInfo<ShopCoupon>> getCoupon(Integer pageNum, Integer pageSize) {
        return adminCouponService.getCoupon(pageNum, pageSize);
    }

    /**
     * 启用优惠券
     *
     * @param statusDTO 优惠券Id
     * @return 结果信息
     */
    @PatchMapping("/activate")
    public Result<String> activate(@RequestBody StatusDTO statusDTO) {
        return adminCouponService.changeStatus(statusDTO.getId(), 1);
    }

    /**
     * 禁用优惠券
     *
     * @param statusDTO 优惠券Id
     * @return 结果信息
     */
    @PatchMapping("/deactivate")
    public Result<String> deactivate(@RequestBody StatusDTO statusDTO) {
        return adminCouponService.changeStatus(statusDTO.getId(), 0);
    }

    /**
     * 更新优惠券信息
     *
     * @param adminShopCouponDTO 数据
     * @return 结果信息
     */
    @PutMapping("/update")
    public Result<String> update(@RequestBody AdminShopCouponDTO adminShopCouponDTO) {
        return adminCouponService.update(adminShopCouponDTO);
    }

    /**
     * 新增优惠券
     *
     * @param adminShopCouponDTO 数据
     * @return 结果消息
     */
    @PostMapping("/add")
    public Result<String> add(@RequestBody AdminShopCouponDTO adminShopCouponDTO) {
        return adminCouponService.add(adminShopCouponDTO);
    }

    /**
     * 删除分类
     *
     * @param id id
     * @return 消息结果
     */
    @DeleteMapping
    public Result<String> delete(@RequestParam int id) {
        return adminCouponService.delete(id);
    }
}
