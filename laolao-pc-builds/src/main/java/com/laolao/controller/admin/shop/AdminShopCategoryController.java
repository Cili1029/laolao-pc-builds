package com.laolao.controller.admin.shop;

import com.github.pagehelper.PageInfo;
import com.laolao.common.annotation.LogSql;
import com.laolao.common.result.Result;
import com.laolao.pojo.shop.dto.AdminShopCategoryDTO;
import com.laolao.pojo.shop.entity.ShopCategory;
import com.laolao.pojo.common.StatusDTO;
import com.laolao.service.admin.shop.AdminShopCategoryService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理端-商品分类相关
 */
@RestController
@RequestMapping("/admin/shop/category")
public class AdminShopCategoryController {
    @Resource
    private AdminShopCategoryService adminShopCategoryService;

    /**
     * 获取商品分类
     * 分组件和整机
     *
     * @return 商品分类数据
     */
    @GetMapping("list")
    public Result<PageInfo<ShopCategory>> list(Integer pageNum, Integer pageSize, String searchContent) {
        return adminShopCategoryService.get(pageNum, pageSize, searchContent);
    }

    /**
     * 启用分类
     *
     * @param statusDTO 分类Id
     * @return 结果信息
     */
    @LogSql(description = "启用商品分类")
    @PatchMapping("/activate")
    public Result<String> activate(@RequestBody StatusDTO statusDTO) {
        return adminShopCategoryService.changeStatus(statusDTO.getId(), 1);
    }

    /**
     * 禁用分类
     *
     * @param statusDTO 分类Id
     * @return 结果信息
     */
    @LogSql(description = "禁用商品分类")
    @PatchMapping("/deactivate")
    public Result<String> deactivate(@RequestBody StatusDTO statusDTO) {
        return adminShopCategoryService.changeStatus(statusDTO.getId(), 0);
    }

    /**
     * 更新分类信息
     *
     * @param adminShopCategoryDTO 更新信息
     * @return 结果信息
     */
    @LogSql(description = "更新商品分类")
    @PatchMapping("/update")
    public Result<String> update(@RequestBody AdminShopCategoryDTO adminShopCategoryDTO) {
        return adminShopCategoryService.update(adminShopCategoryDTO);
    }

    /**
     * 新增分类
     *
     * @param adminShopCategoryDTO 信息
     * @return 结果消息
     */
    @LogSql(description = "添加商品分类")
    @PostMapping("/add")
    public Result<String> add(@RequestBody AdminShopCategoryDTO adminShopCategoryDTO) {
        return adminShopCategoryService.add(adminShopCategoryDTO);
    }

    /**
     * 删除分类
     *
     * @param id id
     * @return 消息结果
     */
    @LogSql(description = "删除商品分类")
    @DeleteMapping("/delete")
    public Result<String> delete(@RequestParam int id) {
        return adminShopCategoryService.delete(id);
    }

    /**
     * 其他功能所需的Category
     *
     * @return 商品分类数据
     */
    @GetMapping("other_need")
    public Result<List<ShopCategory>> need(Integer type) {
        return adminShopCategoryService.getNeed(type);
    }
}
