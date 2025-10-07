package com.laolao.service;

import com.laolao.pojo.vo.ProductsEditionVO;
import com.laolao.pojo.vo.ProductsVO;
import com.laolao.result.Result;

import java.util.List;

public interface ProductsService {
    Result<List<ProductsVO>> listWithCategoryId(int categoryId);

    Result<List<ProductsEditionVO>> listWithProductsId(int productsId);

    Result<List<ProductsVO>> searchByName(int categoryId, String searchContent);
}
