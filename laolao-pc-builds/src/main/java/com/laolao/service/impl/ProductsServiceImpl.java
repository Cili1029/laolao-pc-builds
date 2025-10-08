package com.laolao.service.impl;

import com.laolao.mapper.ProductsMapper;
import com.laolao.pojo.vo.ProductsEditionVO;
import com.laolao.pojo.vo.ProductsVO;
import com.laolao.result.Result;
import com.laolao.service.ProductsService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsServiceImpl implements ProductsService {

    @Resource
    private ProductsMapper productsMapper;

    @Override
    public Result<List<ProductsVO>> listWithCategoryId(int categoryId) {
        List<ProductsVO> productsVoList =  productsMapper.getByConditions(categoryId, null);
        return Result.success(productsVoList);
    }

    @Override
    public Result<List<ProductsEditionVO>> listWithProductsId(int productsId) {
        List<ProductsEditionVO> productsEditionVO =  productsMapper.getByProductsId(productsId);
        return Result.success(productsEditionVO);
    }

    @Override
    public Result<List<ProductsVO>> searchByName(int categoryId, String searchContent) {
        List<ProductsVO> productsVoList =  productsMapper.getByConditions(categoryId, searchContent);
        return Result.success(productsVoList);
    }
}
