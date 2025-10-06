package com.laolao.service;

import com.laolao.pojo.vo.ProductsVO;
import com.laolao.result.Result;

import java.util.List;

public interface ProductsService {
    Result<List<ProductsVO>> listWithId(int categoryId);
}
