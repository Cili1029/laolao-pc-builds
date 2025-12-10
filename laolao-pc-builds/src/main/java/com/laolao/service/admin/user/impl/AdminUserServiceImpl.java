package com.laolao.service.admin.user.impl;

import com.laolao.common.result.Result;
import com.laolao.converter.MapStruct;
import com.laolao.mapper.admin.user.AdminUserMapper;
import com.laolao.mapper.common.SysFileMapper;
import com.laolao.pojo.user.dto.AdminUserUpdateDTO;
import com.laolao.pojo.user.entity.User;
import com.laolao.pojo.user.vo.AdminUserVO;
import com.laolao.service.admin.user.AdminUserService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminUserServiceImpl implements AdminUserService {
    @Resource
    private AdminUserMapper adminUserMapper;
    @Resource
    private MapStruct mapStruct;
    @Resource
    private SysFileMapper sysFileMapper;

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
    public Result<String> changeStatus(int id, int status) {
        User user = new User();
        user.setId(id);
        user.setStatus(status);
        adminUserMapper.updateStatus(user);
        return Result.success(status == 1 ? "已启用！" : "已禁用！");
    }

    @Override
    public Result<String> updateUser(AdminUserUpdateDTO adminUserUpdateDTO) {
        User user = mapStruct.adminUserUpdateDTOToUser(adminUserUpdateDTO);
        adminUserMapper.updateUser(user);
        if (StringUtils.isNoneBlank(adminUserUpdateDTO.getAvatar())) {
            List<String> images = new ArrayList<>();
            images.add(adminUserUpdateDTO.getAvatar());
            sysFileMapper.update(images);
        }
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
