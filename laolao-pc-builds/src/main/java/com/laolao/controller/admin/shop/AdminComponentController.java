package com.laolao.controller.admin.shop;

import com.laolao.common.result.Result;
import com.laolao.pojo.shop.entity.Variant;
import com.laolao.pojo.shop.vo.AdminComponentVO;
import com.laolao.service.admin.shop.AdminComponentService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 客户端组件相关
 */
@RestController
@RequestMapping("/admin/shop/component")
public class AdminComponentController {

    @Resource
    private AdminComponentService adminComponentService;

    /**
     * 获取组件数据
     *
     * @return 组件数据
     */
    @GetMapping
    public Result<List<AdminComponentVO>> list() {
        return adminComponentService.getComponent();
    }

    /**
     * 获取组件版本数据
     *
     * @return 组件数据
     */
    @GetMapping("/variant")
    public Result<List<Variant>> list(@RequestParam int id) {
        return adminComponentService.getVariant(id);
    }
}
