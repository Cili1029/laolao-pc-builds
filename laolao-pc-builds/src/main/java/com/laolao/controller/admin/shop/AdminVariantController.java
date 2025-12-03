package com.laolao.controller.admin.shop;

import com.laolao.common.result.Result;
import com.laolao.pojo.common.StatusDTO;
import com.laolao.pojo.common.StockDTO;
import com.laolao.pojo.shop.entity.Variant;
import com.laolao.pojo.shop.vo.AdminAddVariantVO;
import com.laolao.pojo.shop.vo.AdminUpdateVariantVO;
import com.laolao.service.admin.shop.AdminVariantService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 客户端组件版本相关
 */
@RestController
@RequestMapping("/admin/shop/variant")
public class AdminVariantController {

    @Resource
    private AdminVariantService adminVariantService;

    /**
     * 获取组件版本数据
     *
     * @return 组件数据
     */
    @GetMapping
    public Result<List<Variant>> list(@RequestParam int id) {
        return adminVariantService.getVariant(id);
    }

    /**
     * 启用版本
     *
     * @param statusDTO Id
     * @return 结果信息
     */
    @PatchMapping("/activate")
    public Result<String> activate(@RequestBody StatusDTO statusDTO) {
        return adminVariantService.changeStatus(statusDTO.getId(), 1);
    }

    /**
     * 禁用版本
     *
     * @param statusDTO Id
     * @return 结果信息
     */
    @PatchMapping("/deactivate")
    public Result<String> deactivate(@RequestBody StatusDTO statusDTO) {
        return adminVariantService.changeStatus(statusDTO.getId(), 0);
    }

    /**
     * 删除版本
     *
     * @return 结果信息
     */
    @DeleteMapping
    public Result<String> deactivate(@RequestParam int id) {
        return adminVariantService.delete(id);
    }

    /**
     * 添加版本
     *
     * @param adminAddVariantVO 基础信息
     * @return 新增的版本
     */
    @PostMapping("/add")
    public Result<Variant> add(@RequestBody AdminAddVariantVO adminAddVariantVO) {
        return adminVariantService.add(adminAddVariantVO);
    }

    /**
     * 更新库存
     *
     * @param stockDTO 库存信息
     * @return 结果信息
     */
    @PatchMapping("/stock")
    public Result<String> update(@RequestBody StockDTO stockDTO) {
        return adminVariantService.updateStock(stockDTO);
    }

    /**
     * 更新版本信息
     *
     * @param adminUpdateVariantVO 更新信息
     * @return 结果信息
     */
    @PatchMapping("/update")
    public Result<String> update(@RequestBody AdminUpdateVariantVO adminUpdateVariantVO) {
        return adminVariantService.update(adminUpdateVariantVO);
    }
}
