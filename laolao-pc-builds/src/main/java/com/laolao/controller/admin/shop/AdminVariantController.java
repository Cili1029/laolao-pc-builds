package com.laolao.controller.admin.shop;

import com.laolao.common.result.Result;
import com.laolao.pojo.common.StatusDTO;
import com.laolao.pojo.shop.entity.Variant;
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
     * 启用组件
     *
     * @param statusDTO Id
     * @return 结果信息
     */
    @PatchMapping("/activate")
    public Result<String> activate(@RequestBody StatusDTO statusDTO) {
        return adminVariantService.changeStatus(statusDTO.getId(), 1);
    }

    /**
     * 禁用组件
     *
     * @param statusDTO Id
     * @return 结果信息
     */
    @PatchMapping("/deactivate")
    public Result<String> deactivate(@RequestBody StatusDTO statusDTO) {
        return adminVariantService.changeStatus(statusDTO.getId(), 0);
    }
}
