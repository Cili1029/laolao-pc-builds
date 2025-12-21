package com.laolao.service.admin.shop;

import com.github.pagehelper.PageInfo;
import com.laolao.common.result.Result;
import com.laolao.pojo.shop.dto.AdminShopCategoryDTO;
import com.laolao.pojo.shop.entity.ShopCategory;

import java.util.List;

public interface AdminShopCategoryService {
    Result<PageInfo<ShopCategory>> get(Integer pageNum, Integer pageSize, String searchContent);

    Result<String> changeStatus(int id, int status);

    Result<String> update(AdminShopCategoryDTO adminShopCategoryDTO);

    Result<String> add(AdminShopCategoryDTO adminShopCategoryDTO);

    Result<String> delete(int id);

    Result<List<ShopCategory>> getNeed(Integer type);
}
