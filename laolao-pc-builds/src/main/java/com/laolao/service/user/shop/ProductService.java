package com.laolao.service.user.shop;

import com.github.pagehelper.PageInfo;
import com.laolao.pojo.shop.vo.BundleDetailsVO;
import com.laolao.pojo.shop.vo.ComponentDetailsVO;
import com.laolao.pojo.shop.vo.ProductVO;
import com.laolao.common.result.Result;

import java.util.List;

public interface ProductService {
    Result<List<ProductVO>> getComponentListWithCategoryId(int categoryId);

    Result<List<ProductVO>> getBundleListWithCategoryId(int categoryId);

    Result<List<ProductVO>> searchByName(int categoryId, String searchContent);

    Result<PageInfo<ProductVO>> getHot(Integer pageNum, Integer pageSize);

    Result<ComponentDetailsVO> getComponentDetails(int id);

    Result<BundleDetailsVO> getBundleDetails(int id);
}
