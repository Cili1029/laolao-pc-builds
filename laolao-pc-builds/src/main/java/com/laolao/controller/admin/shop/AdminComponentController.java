package com.laolao.controller.admin.shop;

import com.github.pagehelper.PageInfo;
import com.laolao.common.result.Result;
import com.laolao.pojo.common.StatusDTO;
import com.laolao.pojo.shop.vo.AdminAddComponentVO;
import com.laolao.pojo.shop.vo.AdminComponentVO;
import com.laolao.pojo.shop.vo.AdminUpdateComponentVO;
import com.laolao.service.admin.shop.AdminComponentService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;


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
    public Result<PageInfo<AdminComponentVO>> list(Integer pageNum, Integer pageSize) {
        return adminComponentService.getComponent(pageNum, pageSize);
    }

    /**
     * 启用组件
     *
     * @param statusDTO Id
     * @return 结果信息
     */
    @PatchMapping("/activate")
    public Result<String> activate(@RequestBody StatusDTO statusDTO) {
        return adminComponentService.changeStatus(statusDTO.getId(), 1);
    }

    /**
     * 禁用组件
     *
     * @param statusDTO Id
     * @return 结果信息
     */
    @PatchMapping("/deactivate")
    public Result<String> deactivate(@RequestBody StatusDTO statusDTO) {
        return adminComponentService.changeStatus(statusDTO.getId(), 0);
    }

    /**
     * 删除组件
     *
     * @return 结果信息
     */
    @DeleteMapping
    public Result<String> deactivate(@RequestParam int id) {
        return adminComponentService.delete(id);
    }

    /**
     * 添加组件
     *
     * @param adminAddComponentVO 基础信息
     * @return 结果信息
     */
    @PostMapping("/add")
    public Result<String> add(@RequestBody AdminAddComponentVO adminAddComponentVO) {
        return adminComponentService.add(adminAddComponentVO);
    }

    /**
     * 更新版本信息
     *
     * @param adminUpdateComponentVO 更新信息
     * @return 结果信息
     */
    @PatchMapping("/update")
    public Result<String> update(@RequestBody AdminUpdateComponentVO adminUpdateComponentVO) {
        return adminComponentService.update(adminUpdateComponentVO);
    }
}
