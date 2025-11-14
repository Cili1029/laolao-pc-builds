package com.laolao.service.shop.impl;

import com.laolao.converter.MapStruct;
import com.laolao.mapper.shop.BundleMapper;
import com.laolao.mapper.shop.ComponentMapper;
import com.laolao.pojo.shop.entity.Bundle;
import com.laolao.pojo.shop.vo.ComponentVariantVO;
import com.laolao.pojo.shop.vo.ProductVO;
import com.laolao.common.result.Result;
import com.laolao.service.shop.ProductService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private ComponentMapper componentMapper;
    @Resource
    private BundleMapper bundleMapper;
    @Resource
    private MapStruct mapStruct;

    @Override
    public Result<List<ProductVO>> listWithCategoryId(int categoryId) {
        int productType = componentMapper.getType(categoryId);
        List<ProductVO> productVoList = new ArrayList<>();
        if (productType == 1) {
            productVoList =  componentMapper.getByConditions(categoryId, null);
        } else {
            List<Bundle> bundles = bundleMapper.getByConditions(categoryId, null);
            for (Bundle bundle : bundles) {
                ProductVO productVO = mapStruct.bundleToComponentVO(bundle);
                productVoList.add(productVO);
            }
        }
        return Result.success(productVoList);
    }

    @Override
    public Result<List<ComponentVariantVO>> listWithComponentId(int id, int productType) {
        List<ComponentVariantVO> componentVariantVO;
        if (productType == 1) {
            componentVariantVO =  componentMapper.getByComponentsId(id);
        } else {
            componentVariantVO =bundleMapper.getByComponentsId(id);
        }

        return Result.success(componentVariantVO);
    }

    @Override
    public Result<List<ProductVO>> searchByName(int categoryId, String searchContent) {
        int productType = componentMapper.getType(categoryId);
        List<ProductVO> productVoList = new ArrayList<>();
        if (productType == 1) {
            productVoList =  componentMapper.getByConditions(categoryId, searchContent);
        } else {
            List<Bundle> bundles = bundleMapper.getByConditions(categoryId, searchContent);
            for (Bundle bundle : bundles) {
                ProductVO productVO = mapStruct.bundleToComponentVO(bundle);
                productVoList.add(productVO);
            }
        }
        return Result.success(productVoList);
    }

    @Override
    public Result<List<ProductVO>> getHot(int count) {
        List<ProductVO> productVOS;
        // 查询部件的销售排行
        productVOS = componentMapper.getByConditions(0, null);
        // 查询捆绑销售的销售排行
        List<Bundle> bundleList = bundleMapper.getByConditions(0, null);
        for (Bundle bundle : bundleList) {
            ProductVO productVO = mapStruct.bundleToComponentVO(bundle);
            productVOS.add(productVO);
        }
        //排序
        List<ProductVO> collect = productVOS.stream().sorted(Comparator.comparing(ProductVO::getSales).reversed()).limit(count).toList();
        return Result.success(collect);
    }
}
