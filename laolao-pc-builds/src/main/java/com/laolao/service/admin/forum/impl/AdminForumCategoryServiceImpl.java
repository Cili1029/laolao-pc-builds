package com.laolao.service.admin.forum.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.laolao.common.result.Result;
import com.laolao.converter.MapStruct;
import com.laolao.mapper.admin.forum.AdminForumCategoryMapper;
import com.laolao.mapper.common.SysFileMapper;
import com.laolao.pojo.forum.dto.AdminForumCategoryDTO;
import com.laolao.pojo.forum.entity.ForumCategory;
import com.laolao.service.admin.forum.AdminForumCategoryService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminForumCategoryServiceImpl implements AdminForumCategoryService {

    @Resource
    private AdminForumCategoryMapper adminForumCategoryMapper;
    @Resource
    private MapStruct mapStruct;
    @Resource
    private SysFileMapper sysFileMapper;

    @Override
    public Result<PageInfo<ForumCategory>> get(Integer pageNum, Integer pageSize, String searchContent) {
        PageHelper.startPage(pageNum, pageSize);
        List<ForumCategory> forumCategoryList = StringUtils.isNotBlank(searchContent)
                ? adminForumCategoryMapper.search(searchContent)
                : adminForumCategoryMapper.select();
        PageInfo<ForumCategory> forumCategoryPageInfo = new PageInfo<>(forumCategoryList);
        return Result.success(forumCategoryPageInfo);
    }

    @Override
    public Result<String> changeStatus(int id, int status) {
        ForumCategory forumCategory = new ForumCategory();
        forumCategory.setId(id);
        forumCategory.setStatus(status);
        adminForumCategoryMapper.updateStatus(forumCategory);
        return Result.success(status == 1 ? "已启用！" : "已禁用！");
    }

    @Override
    public Result<String> update(AdminForumCategoryDTO adminForumCategoryDTO) {
        ForumCategory forumCategory = mapStruct.adminForumCategoryDTOToForumCategory(adminForumCategoryDTO);
        adminForumCategoryMapper.update(forumCategory);
        // 修改文件状态
        if (StringUtils.isNoneBlank(adminForumCategoryDTO.getImage())) {
            ArrayList<String> urls = new ArrayList<>();
            urls.add(adminForumCategoryDTO.getImage());
            sysFileMapper.update(urls);
        }
        return Result.success("修改成功");
    }

    @Override
    public Result<String> add(AdminForumCategoryDTO adminForumCategoryDTO) {
        ForumCategory forumCategory = mapStruct.adminForumCategoryDTOToForumCategory(adminForumCategoryDTO);
        adminForumCategoryMapper.insert(forumCategory);
        // 修改文件状态
        if (StringUtils.isNoneBlank(adminForumCategoryDTO.getImage())) {
            ArrayList<String> urls = new ArrayList<>();
            urls.add(adminForumCategoryDTO.getImage());
            sysFileMapper.update(urls);
        }
        return Result.success("新增成功");
    }

    @Override
    public Result<String> delete(int id) {
        adminForumCategoryMapper.delete(id);
        return Result.success("删除成功");
    }

    @Override
    public Result<List<ForumCategory>> getNeed(Integer type) {
        List<ForumCategory> forumCategoryList = adminForumCategoryMapper.select();
        return Result.success(forumCategoryList);
    }
}
