package com.laolao.service;

import com.laolao.common.result.Result;
import com.laolao.pojo.vo.ProductVO;

import java.util.List;

public interface BundleImpl {
    Result<List<ProductVO>> listWithCategoryId(int categoryId);
}
