package com.laolao.service.shop;

import com.laolao.pojo.shop.vo.BundleDetailsVO;
import com.laolao.pojo.shop.vo.ComponentDetailsVO;
import com.laolao.pojo.shop.vo.ProductVO;
import com.laolao.common.result.Result;

import java.util.List;

public interface ProductService {
    Result<List<ProductVO>> listWithCategoryId(int categoryId);

    Result<List<ProductVO>> searchByName(int categoryId, String searchContent);

    Result<List<ProductVO>> getHot(int count);

    Result<ComponentDetailsVO> getComponentDetails(int id);

    Result<BundleDetailsVO> getBundleDetails(int id);
}
