package com.laolao.controller.admin.user;

import com.laolao.common.result.Result;
import com.laolao.pojo.common.StatusDTO;
import com.laolao.pojo.user.dto.AdminUserUpdateDTO;
import com.laolao.pojo.user.vo.AdminUserVO;
import com.laolao.service.admin.user.AdminUserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Result<List<AdminUserVO>> getEmailCode() {
        return adminUserService.getUser();
    }

    /**
     * 启用账户
     *
     * @param statusDTO 账户Id
     * @return 结果信息
     */
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
    @PatchMapping("/update")
    public Result<String> update(@RequestBody AdminUserUpdateDTO adminUserUpdateDTO) {
        return adminUserService.updateUser(adminUserUpdateDTO);
    }

    /**
     * 模糊搜索
     *
     * @param searchContent 搜索内容
     * @return 用户数据
     */
    @GetMapping("/search")
    public Result<List<AdminUserVO>> searchUser(@RequestParam String searchContent) {
        return adminUserService.searchUser(searchContent);
    }
}

