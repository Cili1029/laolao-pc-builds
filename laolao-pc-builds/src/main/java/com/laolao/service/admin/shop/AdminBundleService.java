package com.laolao.service.admin.shop;

import com.github.pagehelper.PageInfo;
import com.laolao.common.result.Result;
import com.laolao.pojo.common.StockOrQuantityDTO;
import com.laolao.pojo.shop.dto.AdminAddBundleDTO;
import com.laolao.pojo.shop.dto.AdminBundleAddVariantDTO;
import com.laolao.pojo.shop.dto.AdminUpdateBundleDTO;
import com.laolao.pojo.shop.vo.*;

import java.util.List;


public interface AdminBundleService {
    Result<PageInfo<AdminBundleVO>> getBundle(Integer pageNum, Integer pageSize);

    Result<String> changeStatus(int id, int status);

    Result<String> delete(int id);

    Result<String> add(AdminAddBundleDTO adminAddBundleDTO);

    Result<String> update(AdminUpdateBundleDTO adminUpdateBundleDTO);

    Result<String> updateStock(StockOrQuantityDTO stockOrQuantityDTO);

    Result<List<AdminBundleVariantVO>> getVariant(int id);

    Result<String> updateQuantity(StockOrQuantityDTO stockOrQuantityDTO);

    Result<String> deleteVariant(int id);

    Result<List<AdminBundleVariantVO>> searchVariant(String searchContent);

    Result<String> addVariant(AdminBundleAddVariantDTO adminBundleAddVariantDTO);
}
