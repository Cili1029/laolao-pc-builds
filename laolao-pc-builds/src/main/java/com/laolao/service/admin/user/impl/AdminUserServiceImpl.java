package com.laolao.service.admin.user.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
import org.springframework.beans.BeanUtils;
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
    public Result<PageInfo<AdminUserVO>> getUser(Integer pageNum, Integer pageSize, String searchContent) {
        PageHelper.startPage(pageNum, pageSize);
        ArrayList<User> userList = StringUtils.isNotBlank(searchContent)
                ? adminUserMapper.selectUser(searchContent)
                : adminUserMapper.selectUser(null);

        List<AdminUserVO> adminUserVOS = new ArrayList<>();
        for (User user : userList) {
            AdminUserVO adminUserVO;
            adminUserVO = mapStruct.userToAdminUserVO(user);
            adminUserVOS.add(adminUserVO);
        }

        PageInfo<User> userPageInfo = new PageInfo<>(userList);
        PageInfo<AdminUserVO> adminUserVOPageInfo = new PageInfo<>(adminUserVOS);
        BeanUtils.copyProperties(userPageInfo, adminUserVOPageInfo);
        adminUserVOPageInfo.setList(adminUserVOS);

        return Result.success(adminUserVOPageInfo);
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
}
