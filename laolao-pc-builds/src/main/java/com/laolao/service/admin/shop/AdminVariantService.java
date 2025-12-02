package com.laolao.service.admin.shop;

import com.laolao.common.result.Result;
import com.laolao.pojo.shop.entity.Variant;

import java.util.List;

public interface AdminVariantService {
    Result<List<Variant>> getVariant(int id);

    Result<String> changeStatus(int id, int status);
}
