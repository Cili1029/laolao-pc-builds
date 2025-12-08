package com.laolao.service.admin.forum.impl;

import com.laolao.common.context.UserContext;
import com.laolao.common.result.Result;
import com.laolao.mapper.admin.forum.AdminForumCategoryMapper;
import com.laolao.pojo.forum.dto.AdminForumCategoryDTO;
import com.laolao.pojo.forum.entity.ForumCategory;
import com.laolao.service.admin.forum.AdminForumCategoryService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminForumCategoryServiceImpl implements AdminForumCategoryService {

    @Resource
    private AdminForumCategoryMapper adminForumCategoryMapper;

    @Override
    public Result<List<ForumCategory>> get() {
        List<ForumCategory> forumCategoryList=  adminForumCategoryMapper.select();
        return Result.success(forumCategoryList);
    }

    @Override
    public Result<String> changeStatus(int id, int status) {
        adminForumCategoryMapper.updateStatus(id, status);
        return Result.success(status == 1 ? "已启用！" : "已禁用！");
    }

    @Override
    public Result<String> update(AdminForumCategoryDTO adminForumCategoryDTO) {
        adminForumCategoryMapper.update(adminForumCategoryDTO);
        return Result.success("修改成功");
    }

    @Override
    public Result<String> add(AdminForumCategoryDTO adminForumCategoryDTO) {
        adminForumCategoryDTO.setCreatedBy(UserContext.getCurrentId());
        adminForumCategoryMapper.insert(adminForumCategoryDTO);
        return Result.success("新增成功");
    }

    @Override
    public Result<String> delete(int id) {
        adminForumCategoryMapper.delete(id);
        return Result.success("删除成功");
    }
}
