package com.laolao.service.admin.shop;

import com.laolao.common.result.Result;
import com.laolao.pojo.common.StockDTO;
import com.laolao.pojo.shop.entity.Variant;
import com.laolao.pojo.shop.vo.AdminAddVariantVO;
import com.laolao.pojo.shop.vo.AdminUpdateVariantVO;

import java.util.List;

public interface AdminVariantService {
    Result<List<Variant>> getVariant(int id);

    Result<String> changeStatus(int id, int status);

    Result<String> delete(int id);

    Result<Variant> add(AdminAddVariantVO adminAddVariantVO);

    Result<String> updateStock(StockDTO stockDTO);

    Result<String> update(AdminUpdateVariantVO adminUpdateVariantVO);
}
