package com.laolao.service.impl;

import com.laolao.converter.MapStruct;
import com.laolao.mapper.BundleMapper;
import com.laolao.mapper.ComponentMapper;
import com.laolao.pojo.entity.Bundle;
import com.laolao.pojo.vo.ComponentVariantVO;
import com.laolao.pojo.vo.ProductVO;
import com.laolao.common.result.Result;
import com.laolao.service.ProductService;
import jakarta.annotation.Resource;
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

    @Override
    public Result<List<ProductVO>> listWithCategoryId(int categoryId) {
        int productType = componentMapper.getType(categoryId);
        List<ProductVO> productVoList = new ArrayList<>();
        if (productType == 1) {
            productVoList =  componentMapper.getByConditions(categoryId, null);
        } else {
            List<Bundle> bundles = bundleMapper.getByConditions(categoryId, null);
            for (Bundle bundle : bundles) {
                ProductVO productVO = mapStruct.BundleToComponentVO(bundle);
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
                ProductVO productVO = mapStruct.BundleToComponentVO(bundle);
                productVoList.add(productVO);
            }
        }
        return Result.success(productVoList);
    }
}
