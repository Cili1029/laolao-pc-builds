package com.laolao.service.user.shop.impl;

import com.laolao.converter.MapStruct;
import com.laolao.mapper.user.shop.ShopCategoryMapper;
import com.laolao.pojo.shop.entity.ShopCategory;
import com.laolao.pojo.shop.vo.ShopCategoryVO;
import com.laolao.common.result.Result;
import com.laolao.service.user.shop.ShopCategoryService;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {
    @Resource
    private ShopCategoryMapper shopCategoryMapper;
    @Resource
    private MapStruct mapStruct;

    @Override
    @Cacheable(value = "shop#1440", key = "T(com.laolao.common.constant.RedisConstant).SHOP_CATEGORY")
    public Result<List<ShopCategoryVO>> list() {
        List<ShopCategory> shopCategoryList = shopCategoryMapper.get();

        List<ShopCategoryVO> shopCategoryVOList = new ArrayList<>();
        for (ShopCategory shopCategory : shopCategoryList) {
            ShopCategoryVO shopCategoryVO = mapStruct.ShopCategoryToCategoryVO(shopCategory);
            shopCategoryVOList.add(shopCategoryVO);
        }
        return Result.success(shopCategoryVOList);
    }
}
