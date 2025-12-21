package com.laolao.controller.admin.user;

import com.github.pagehelper.PageInfo;
import com.laolao.common.annotation.LogSql;
import com.laolao.common.result.Result;
import com.laolao.pojo.common.StatusDTO;
import com.laolao.pojo.user.dto.AdminUserUpdateDTO;
import com.laolao.pojo.user.vo.AdminUserVO;
import com.laolao.service.admin.user.AdminUserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;


/**
 * 管理端-用户相关
 */
@RestController
@RequestMapping("/admin/user")
public class AdminUserController {
    @Resource
    private AdminUserService adminUserService;

    /**
     * 获取所有用户数据
     *
     * @return 用户数据
     */
    @GetMapping
    public Result<PageInfo<AdminUserVO>> getUser(Integer pageNum, Integer pageSize, String searchContent) {
        return adminUserService.getUser(pageNum, pageSize, searchContent);
    }

    /**
     * 启用账户
     *
     * @param statusDTO 账户Id
     * @return 结果信息
     */
    @LogSql(description = "启用账户")
    @PatchMapping("/status/activate")
    public Result<String> activate(@RequestBody StatusDTO statusDTO) {
        return adminUserService.changeStatus(statusDTO.getId(), 1);
    }

    /**
     * 禁用账户
     *
     * @param statusDTO 账户Id
     * @return 结果信息
     */
    @LogSql(description = "禁用账户")
    @PatchMapping("/status/deactivate")
    public Result<String> deactivate(@RequestBody StatusDTO statusDTO) {
        return adminUserService.changeStatus(statusDTO.getId(), 0);
    }

    /**
     * 更新账户信息
     *
     * @param adminUserUpdateDTO 更新信息
     * @return 结果信息
     */
    @LogSql(description = "更新账户")
    @PatchMapping("/update")
    public Result<String> update(@RequestBody AdminUserUpdateDTO adminUserUpdateDTO) {
        return adminUserService.updateUser(adminUserUpdateDTO);
    }
}

