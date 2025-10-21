package com.laolao.service.shop;

import com.laolao.pojo.shop.vo.ShopCategoryVO;
import com.laolao.common.result.Result;

import java.util.List;

public interface ShopCategoryService {
    Result<List<ShopCategoryVO>> list();
}
