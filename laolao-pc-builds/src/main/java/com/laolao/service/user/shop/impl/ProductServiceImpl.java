package com.laolao.service.user.shop.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.laolao.common.constant.ProductConstant;
import com.laolao.common.constant.RedisConstant;
import com.laolao.converter.MapStruct;
import com.laolao.mapper.user.shop.BundleMapper;
import com.laolao.mapper.user.shop.ComponentMapper;
import com.laolao.pojo.shop.entity.Bundle;
import com.laolao.pojo.shop.entity.Component;
import com.laolao.pojo.shop.entity.Variant;
import com.laolao.pojo.shop.vo.*;
import com.laolao.common.result.Result;
import com.laolao.service.user.shop.ProductService;
import jakarta.annotation.Resource;
import org.redisson.api.RBloomFilter;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private ComponentMapper componentMapper;
    @Resource
    private BundleMapper bundleMapper;
    @Resource
    private MapStruct mapStruct;
    @Resource
    private RBloomFilter<Integer> componentBloomFilter;
    @Resource
    private RBloomFilter<Integer> bundleBloomFilter;

    @Override
    @Cacheable(value = RedisConstant.Shop.CACHE_NAME + RedisConstant.Expire.STR_HOUR_12,
            key = "T(com.laolao.common.constant.RedisConstant$Shop).COMPONENT_SIMPLE + #categoryId")
    public Result<List<ProductVO>> getComponentListWithCategoryId(int categoryId) {
        List<ProductVO> productVoList;
        productVoList = componentMapper.getByConditions(categoryId, null);
        return Result.success(productVoList);
    }


    @Override
    @Cacheable(value = RedisConstant.Shop.CACHE_NAME + RedisConstant.Expire.STR_HOUR_12,
            key = "T(com.laolao.common.constant.RedisConstant$Shop).BUNDLE_SIMPLE + #categoryId")
    public Result<List<ProductVO>> getBundleListWithCategoryId(int categoryId) {
        List<ProductVO> productVoList = new ArrayList<>();
        List<Bundle> bundles = bundleMapper.getByConditions(categoryId, null);
        for (Bundle bundle : bundles) {
            ProductVO productVO = mapStruct.bundleToProductVO(bundle);
            productVoList.add(productVO);
        }
        return Result.success(productVoList);
    }

    @Override
    public Result<List<ProductVO>> searchByName(int categoryId, String searchContent) {
        if (categoryId == ProductConstant.ALL) {
            // 全部搜索
            List<ProductVO> productVOS = componentMapper.getByConditions(0, searchContent);
            List<Bundle> bundles = bundleMapper.getByConditions(0, searchContent);
            for (Bundle bundle : bundles) {
                ProductVO productVO = mapStruct.bundleToProductVO(bundle);
                productVOS.add(productVO);
            }
            return Result.success(productVOS);
        }

        int productType = componentMapper.getType(categoryId);
        List<ProductVO> productVoList = new ArrayList<>();
        if (productType == ProductConstant.COMPONENT) {
            productVoList = componentMapper.getByConditions(categoryId, searchContent);
        } else {
            List<Bundle> bundles = bundleMapper.getByConditions(categoryId, searchContent);
            for (Bundle bundle : bundles) {
                ProductVO productVO = mapStruct.bundleToProductVO(bundle);
                productVoList.add(productVO);
            }
        }
        return Result.success(productVoList);
    }

    @Override
    @Cacheable(value = RedisConstant.Shop.CACHE_NAME + RedisConstant.Expire.STR_HOUR_1,
            key = "#pageSize == 5 ? (T(com.laolao.common.constant.RedisConstant$Shop).HOT_HOME) : (T(com.laolao.common.constant.RedisConstant$Shop).HOT_ALL + #pageNum)")
    public Result<PageInfo<ProductVO>> getHot(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ProductVO> Res = componentMapper.getHot();
        Result<PageInfo<ProductVO>> success = new Result<>();
        success.setData(new PageInfo<>(Res));
        return success;
    }

    @Override
    @Cacheable(value = RedisConstant.Shop.CACHE_NAME + RedisConstant.Expire.STR_HOUR_12,
            key = "T(com.laolao.common.constant.RedisConstant$Shop).COMPONENT_DETAIL + #id")
    public Result<ComponentDetailsVO> getComponentDetails(int id) {
        // 先查布隆
        if (!componentBloomFilter.contains(id)) {
            return Result.error(null);
        }

        ComponentDetailsVO componentDetailsVO;
        // 部件
        Component component = componentMapper.getProduct(id);
        componentDetailsVO = mapStruct.componentToProductDetailsVO(component);
        // 部件的变种
        List<Variant> variants = componentMapper.selectVariants(id);
        List<VariantVO> variantVOS = new ArrayList<>();
        for (Variant variant : variants) {
            VariantVO variantVO = mapStruct.variantToVariantVO(variant);
            variantVOS.add(variantVO);
        }
        componentDetailsVO.setVariants(variantVOS);
        return Result.success(componentDetailsVO);
    }

    @Override
    @Cacheable(value = RedisConstant.Shop.CACHE_NAME + RedisConstant.Expire.STR_HOUR_12,
            key = "T(com.laolao.common.constant.RedisConstant$Shop).BUNDLE_DETAIL + #id")
    public Result<BundleDetailsVO> getBundleDetails(int id) {
        // 先查布隆
        if (!bundleBloomFilter.contains(id)) {
            return Result.error(null);
        }

        BundleDetailsVO bundleDetailsVO;
        Bundle bundle = bundleMapper.selectBundle(id);
        bundleDetailsVO = mapStruct.bundleToProductDetailsVO(bundle);
        // 捆绑包包含的变种
        List<VariantVO> variantVOS = bundleMapper.selectBundleVariants(id);
        bundleDetailsVO.setVariants(variantVOS);
        return Result.success(bundleDetailsVO);
    }
}
