package com.laolao.service.admin.shop;

import com.laolao.common.result.Result;
import com.laolao.pojo.common.StockOrQuantityDTO;
import com.laolao.pojo.shop.entity.Variant;
import com.laolao.pojo.shop.dto.AdminAddVariantDTO;
import com.laolao.pojo.shop.dto.AdminUpdateVariantDTO;

import java.util.List;

public interface AdminVariantService {
    Result<List<Variant>> getVariant(int id);

    Result<String> changeStatus(int id, int status);

    Result<String> delete(int id);

    Result<Variant> add(AdminAddVariantDTO adminAddVariantDTO);

    Result<String> updateStock(StockOrQuantityDTO stockOrQuantityDTO);

    Result<String> update(AdminUpdateVariantDTO adminUpdateVariantDTO);
}
