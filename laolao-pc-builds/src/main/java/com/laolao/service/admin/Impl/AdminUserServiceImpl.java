package com.laolao.service.admin.Impl;

import com.laolao.common.result.Result;
import com.laolao.converter.MapStruct;
import com.laolao.mapper.admin.user.AdminUserMapper;
import com.laolao.pojo.user.dto.AdminUserUpdateDTO;
import com.laolao.pojo.user.entity.User;
import com.laolao.pojo.user.vo.AdminUserVO;
import com.laolao.service.admin.AdminUserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminUserServiceImpl implements AdminUserService {
    @Resource
    private AdminUserMapper adminUserMapper;
    @Resource
    private MapStruct mapStruct;

    @Override
    public Result<List<AdminUserVO>> getUser() {
        List<AdminUserVO> adminUserVOS = new ArrayList<>();

        ArrayList<User> userList = adminUserMapper.selectUser(null);
        for (User user : userList) {
            AdminUserVO adminUserVO;
            adminUserVO = mapStruct.userToAdminUserVO(user);
            adminUserVOS.add(adminUserVO);
        }

        return Result.success(adminUserVOS);
    }

    @Override
    public Result<String> changeStatus(int userId, int status) {
        adminUserMapper.updateStatus(userId, status);
        return Result.success(status == 1 ? "已启用！" : "已禁用！");
    }

    @Override
    public Result<String> updateUser(AdminUserUpdateDTO adminUserUpdateDTO) {
        adminUserMapper.updateUser(adminUserUpdateDTO);
        return Result.success("修改成功");
    }

    @Override
    public Result<List<AdminUserVO>> searchUser(String searchContent) {
        List<AdminUserVO> adminUserVOS = new ArrayList<>();

        ArrayList<User> userList = adminUserMapper.selectUser(searchContent);
        for (User user : userList) {
            AdminUserVO adminUserVO;
            adminUserVO = mapStruct.userToAdminUserVO(user);
            adminUserVOS.add(adminUserVO);
        }

        return Result.success(adminUserVOS);
    }
}
