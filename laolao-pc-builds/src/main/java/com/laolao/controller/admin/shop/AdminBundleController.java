package com.laolao.controller.admin.shop;

import com.github.pagehelper.PageInfo;
import com.laolao.common.annotation.LogSql;
import com.laolao.common.result.Result;
import com.laolao.pojo.common.StatusDTO;
import com.laolao.pojo.common.StockOrQuantityDTO;
import com.laolao.pojo.shop.dto.AdminAddBundleDTO;
import com.laolao.pojo.shop.dto.AdminBundleAddVariantDTO;
import com.laolao.pojo.shop.dto.AdminUpdateBundleDTO;
import com.laolao.pojo.shop.vo.*;
import com.laolao.service.admin.shop.AdminBundleService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 管理端整机相关
 */
@RestController
@RequestMapping("/admin/shop/bundle")
public class AdminBundleController {

    @Resource
    private AdminBundleService adminBundleService;

    /**
     * 获取整机数据
     *
     * @return 组件数据
     */
    @GetMapping
    public Result<PageInfo<AdminBundleVO>> list(Integer pageNum, Integer pageSize) {
        return adminBundleService.getBundle(pageNum, pageSize);
    }

    /**
     * 启用整机
     *
     * @param statusDTO Id
     * @return 结果信息
     */
    @LogSql(description = "启用整机")
    @PatchMapping("/activate")
    public Result<String> activate(@RequestBody StatusDTO statusDTO) {
        return adminBundleService.changeStatus(statusDTO.getId(), 1);
    }

    /**
     * 禁用整机
     *
     * @param statusDTO Id
     * @return 结果信息
     */
    @LogSql(description = "禁用整机")
    @PatchMapping("/deactivate")
    public Result<String> deactivate(@RequestBody StatusDTO statusDTO) {
        return adminBundleService.changeStatus(statusDTO.getId(), 0);
    }

    /**
     * 删除整机
     *
     * @return 结果信息
     */
    @LogSql(description = "删除整机")
    @DeleteMapping
    public Result<String> deactivate(@RequestParam int id) {
        return adminBundleService.delete(id);
    }

    /**
     * 添加整机
     *
     * @param adminAddBundleDTO 基础信息
     * @return 结果信息
     */
    @LogSql(description = "添加整机")
    @PostMapping("/add")
    public Result<String> add(@RequestBody AdminAddBundleDTO adminAddBundleDTO) {
        return adminBundleService.add(adminAddBundleDTO);
    }

    /**
     * 更新库存
     *
     * @param stockOrQuantityDTO 库存信息
     * @return 结果信息
     */
    @LogSql(description = "更新整机库存")
    @PatchMapping("/stock")
    public Result<String> update(@RequestBody StockOrQuantityDTO stockOrQuantityDTO) {
        return adminBundleService.updateStock(stockOrQuantityDTO);
    }

    /**
     * 更新整机信息
     *
     * @param adminUpdateBundleDTO 更新信息
     * @return 结果信息
     */
    @PatchMapping("/update")
    public Result<String> update(@RequestBody AdminUpdateBundleDTO adminUpdateBundleDTO) {
        return adminBundleService.update(adminUpdateBundleDTO);
    }

    /**
     * 获取所包含的组件版本数据
     *
     * @return 组件数据
     */
    @LogSql(description = "更新整机")
    @GetMapping("/variant")
    public Result<List<AdminBundleVariantVO>> list(@RequestParam int id) {
        return adminBundleService.getVariant(id);
    }

    /**
     * 修改所包含的组件数量
     *
     * @param stockOrQuantityDTO 更新信息
     * @return 结果信息
     */
    @LogSql(description = "更新整机组件数量")
    @PatchMapping("/variant/quantity")
    public Result<String> updateQuantity(@RequestBody StockOrQuantityDTO stockOrQuantityDTO) {
        return adminBundleService.updateQuantity(stockOrQuantityDTO);
    }

    /**
     * 删除所包含的组件
     *
     * @param id 组件
     * @return 结果信息
     */
    @LogSql(description = "删除整机组件")
    @DeleteMapping("/variant/delete")
    public Result<String> deleteVariant(@RequestParam int id) {
        return adminBundleService.deleteVariant(id);
    }

    /**
     * 搜索组件
     * @param searchContent 内容
     *
     * @return 组件数据
     */
    @GetMapping("/variant/search")
    public Result<List<AdminBundleVariantVO>> searchVariant(@RequestParam String searchContent) {
        return adminBundleService.searchVariant(searchContent);
    }

    /**
     * 添加组件
     *
     * @param adminBundleAddVariantDTO 基础信息
     * @return 结果信息
     */
    @LogSql(description = "添加整机组件")
    @PatchMapping("/variant/add")
    public Result<String> addVariant(@RequestBody AdminBundleAddVariantDTO adminBundleAddVariantDTO) {
        return adminBundleService.addVariant(adminBundleAddVariantDTO);
    }
}
