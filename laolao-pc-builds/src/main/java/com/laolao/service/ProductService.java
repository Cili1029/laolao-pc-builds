package com.laolao.service;

import com.laolao.pojo.vo.ComponentVariantVO;
import com.laolao.pojo.vo.ProductVO;
import com.laolao.common.result.Result;

import java.util.List;

public interface ProductService {
    Result<List<ProductVO>> listWithCategoryId(int categoryId);

    Result<List<ComponentVariantVO>> listWithComponentId(int id, int productType);

    Result<List<ProductVO>> searchByName(int categoryId, String searchContent);
}
