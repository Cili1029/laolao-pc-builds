package com.laolao.service.impl;

import com.laolao.converter.MapStruct;
import com.laolao.mapper.ProductsMapper;
import com.laolao.pojo.entity.Products;
import com.laolao.pojo.vo.ProductsVO;
import com.laolao.result.Result;
import com.laolao.service.ProductsService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductsServiceImpl implements ProductsService {

    @Resource
    private ProductsMapper productsMapper;
    @Resource
    private MapStruct mapStruct;

    @Override
    public Result<List<ProductsVO>> listWithId(int categoryId) {
        List<Products> productslist =  productsMapper.getByCategoryId(categoryId);

        List<ProductsVO> productsVoList = new ArrayList<>();

        for (Products products : productslist) {
            ProductsVO productsVO = mapStruct.productsToProductsVO(products);
            productsVoList.add(productsVO);

        }
        return Result.success(productsVoList);
    }
}
