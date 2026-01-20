package com.laolao.service.admin.shop.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.laolao.common.constant.RedisConstant;
import com.laolao.common.result.Result;
import com.laolao.converter.MapStruct;
import com.laolao.mapper.admin.shop.AdminShopCategoryMapper;
import com.laolao.mapper.common.SysFileMapper;
import com.laolao.pojo.shop.dto.AdminShopCategoryDTO;
import com.laolao.pojo.shop.entity.ShopCategory;
import com.laolao.service.admin.shop.AdminShopCategoryService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminShopCategoryServiceImpl implements AdminShopCategoryService {

    @Resource
    private AdminShopCategoryMapper adminShopCategoryMapper;
    @Resource
    private MapStruct mapStruct;
    @Resource
    private SysFileMapper sysFileMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static final String CATEGORY = RedisConstant.Shop.CACHE_NAME + "::" + RedisConstant.Shop.CATEGORY;

    @Override
    public Result<PageInfo<ShopCategory>> get(Integer pageNum, Integer pageSize, String searchContent) {
        PageHelper.startPage(pageNum, pageSize);
        List<ShopCategory> shopCategoryList = StringUtils.isNotBlank(searchContent)
                ? adminShopCategoryMapper.search(searchContent)
                : adminShopCategoryMapper.select(null);
        PageInfo<ShopCategory> shopCategoryPageInfo = new PageInfo<>(shopCategoryList);
        return Result.success(shopCategoryPageInfo);
    }

    @Override
    public Result<String> changeStatus(int id, int status) {
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setId(id);
        shopCategory.setStatus(status);
        adminShopCategoryMapper.updateStatus(shopCategory);
        // 删除Redis缓存
        stringRedisTemplate.delete(CATEGORY);
        return Result.success(status == 1 ? "已启用！" : "已禁用！");
    }

    @Override
    public Result<String> update(AdminShopCategoryDTO adminShopCategoryDTO) {
        ShopCategory shopCategory = mapStruct.adminShopCategoryDTOToShopCategory(adminShopCategoryDTO);
        adminShopCategoryMapper.update(shopCategory);
        // 修改文件状态
        if (StringUtils.isNoneBlank(adminShopCategoryDTO.getImage())) {
            List<String> images = new ArrayList<>();
            images.add(adminShopCategoryDTO.getImage());
            sysFileMapper.update(images);
        }
        // 删除Redis缓存
        stringRedisTemplate.delete(CATEGORY);
        return Result.success("修改成功");
    }

    @Override
    public Result<String> add(AdminShopCategoryDTO adminShopCategoryDTO) {
        ShopCategory shopCategory = mapStruct.adminShopCategoryDTOToShopCategory(adminShopCategoryDTO);
        adminShopCategoryMapper.insert(shopCategory);
        // 修改文件状态
        if (StringUtils.isNoneBlank(adminShopCategoryDTO.getImage())) {
            List<String> images = new ArrayList<>();
            images.add(adminShopCategoryDTO.getImage());
            sysFileMapper.update(images);
        }
        // 删除Redis缓存
        stringRedisTemplate.delete(CATEGORY);
        return Result.success("新增成功");
    }

    @Override
    public Result<String> delete(int id) {
        adminShopCategoryMapper.delete(id);
        // 删除Redis缓存
        stringRedisTemplate.delete(CATEGORY);
        return Result.success("删除成功");
    }

    @Override
    public Result<List<ShopCategory>> getNeed(Integer type) {
        List<ShopCategory> categoryList = adminShopCategoryMapper.select(type);
        return Result.success(categoryList);
    }
}
