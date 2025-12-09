package com.laolao.service.admin.shop.impl;

import com.laolao.common.context.UserContext;
import com.laolao.common.result.Result;
import com.laolao.converter.MapStruct;
import com.laolao.mapper.admin.shop.AdminShopCategoryMapper;
import com.laolao.pojo.shop.dto.AdminShopCategoryDTO;
import com.laolao.pojo.shop.entity.ShopCategory;
import com.laolao.service.admin.shop.AdminShopCategoryService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminShopCategoryServiceImpl implements AdminShopCategoryService {

    @Resource
    private AdminShopCategoryMapper adminShopCategoryMapper;
    @Resource
    private MapStruct mapStruct;

    @Override
    public Result<List<ShopCategory>> get(int type) {
        List<ShopCategory> shopCategoryList =  adminShopCategoryMapper.select(type);
        return Result.success(shopCategoryList);
    }

    @Override
    public Result<String> changeStatus(int id, int status) {
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setId(id);
        shopCategory.setStatus(status);
        adminShopCategoryMapper.updateStatus(shopCategory);
        return Result.success(status == 1 ? "已启用！" : "已禁用！");
    }

    @Override
    public Result<String> update(AdminShopCategoryDTO adminShopCategoryDTO) {
        ShopCategory shopCategory = mapStruct.adminShopCategoryDTOToShopCategory(adminShopCategoryDTO);
        adminShopCategoryMapper.update(shopCategory);
        return Result.success("修改成功");
    }

    @Override
    public Result<String> add(AdminShopCategoryDTO adminShopCategoryDTO) {
        ShopCategory shopCategory = mapStruct.adminShopCategoryDTOToShopCategory(adminShopCategoryDTO);
        adminShopCategoryMapper.insert(shopCategory);
        return Result.success("新增成功");
    }

    @Override
    public Result<String> delete(int id) {
        adminShopCategoryMapper.delete(id);
        return Result.success("删除成功");
    }
}
