package com.laolao.service.admin.forum;

import com.laolao.common.result.Result;
import com.laolao.pojo.forum.dto.AdminForumCategoryDTO;
import com.laolao.pojo.forum.entity.ForumCategory;
import com.laolao.pojo.shop.dto.AdminShopCategoryDTO;
import com.laolao.pojo.shop.entity.ShopCategory;

import java.util.List;

public interface AdminForumCategoryService {
    Result<List<ForumCategory>> get();

    Result<String> changeStatus(int id, int status);

    Result<String> update(AdminForumCategoryDTO adminForumCategoryDTO);

    Result<String> add(AdminForumCategoryDTO adminForumCategoryDTO);

    Result<String> delete(int id);
}
