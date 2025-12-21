package com.laolao.service.admin.forum;

import com.github.pagehelper.PageInfo;
import com.laolao.common.result.Result;
import com.laolao.pojo.forum.dto.AdminForumCategoryDTO;
import com.laolao.pojo.forum.entity.ForumCategory;

import java.util.List;

public interface AdminForumCategoryService {
    Result<PageInfo<ForumCategory>> get(Integer pageNum, Integer pageSize, String searchContent);

    Result<String> changeStatus(int id, int status);

    Result<String> update(AdminForumCategoryDTO adminForumCategoryDTO);

    Result<String> add(AdminForumCategoryDTO adminForumCategoryDTO);

    Result<String> delete(int id);

    Result<List<ForumCategory>> getNeed(Integer type);
}
