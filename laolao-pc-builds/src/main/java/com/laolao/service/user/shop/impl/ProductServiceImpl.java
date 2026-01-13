package com.laolao.service.user.shop.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private ComponentMapper componentMapper;
    @Resource
    private BundleMapper bundleMapper;
    @Resource
    private MapStruct mapStruct;
    @Resource
    StringRedisTemplate stringRedisTemplate;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
//    @Cacheable(value = "shop#720", key = "T(com.laolao.common.constant.RedisConstant).SHOP_COMPONENT_SIMPLE_KEY + #categoryId")
    public Result<List<ProductVO>> getComponentListWithCategoryId(int categoryId) {
        List<ProductVO> productVoList;
        productVoList = componentMapper.getByConditions(categoryId, null);
        return Result.success(productVoList);
    }


    @Override
//    @Cacheable(value = "shop#720", key = "T(com.laolao.common.constant.RedisConstant).SHOP_BUNDLE_SIMPLE_KEY + #categoryId")
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
        if (categoryId == 0) {
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
        if (productType == 1) {
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
    public Result<List<ProductVO>> getHot(int count) {
        List<ProductVO> productVOS;
        // 查询部件的销售排行
        productVOS = componentMapper.getHot(count);
        // 查询捆绑销售的销售排行
        List<Bundle> bundleList = bundleMapper.getHot(count);
        for (Bundle bundle : bundleList) {
            ProductVO productVO = mapStruct.bundleToProductVO(bundle);
            productVOS.add(productVO);
        }
        // 排序
        // 为0时是获得全部产品
        List<ProductVO> sortedRes = productVOS.stream().sorted(Comparator.comparing(ProductVO::getSales).reversed()).toList();
        if (count != 0) {
            sortedRes = sortedRes.stream().limit(count).toList();
        }

        Result<List<ProductVO>> success = new Result<>();
        success.setData(sortedRes);
        try {
            String key ="shop::" + (count == 0 ? RedisConstant.SHOP_HOT_ALL : RedisConstant.SHOP_HOT_HOME);
            long expireTime = count == 0 ? 12L : 1L;
            stringRedisTemplate.opsForValue().set(key, OBJECT_MAPPER.writeValueAsString(success), expireTime, TimeUnit.HOURS);
        } catch (JsonProcessingException ignored) {
        }
        return success;
    }

    @Override
    @Cacheable(value = "shop#720", key = "T(com.laolao.common.constant.RedisConstant).SHOP_COMPONENT_DETAIL_KEY + #id")
    public Result<ComponentDetailsVO> getComponentDetails(int id) {
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
    @Cacheable(value = "shop#720", key = "T(com.laolao.common.constant.RedisConstant).SHOP_BUNDLE_DETAIL_KEY + #id")
    public Result<BundleDetailsVO> getBundleDetails(int id) {
        BundleDetailsVO bundleDetailsVO;
        Bundle bundle = bundleMapper.selectBundle(id);
        bundleDetailsVO = mapStruct.bundleToProductDetailsVO(bundle);
        // 捆绑包包含的变种
        List<VariantVO> variantVOS = bundleMapper.selectBundleVariants(id);
        bundleDetailsVO.setVariants(variantVOS);
        return Result.success(bundleDetailsVO);
    }
}
