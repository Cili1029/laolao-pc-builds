package com.laolao.service.admin.shop;

import com.laolao.common.result.Result;
import com.laolao.pojo.shop.entity.Variant;
import com.laolao.pojo.shop.vo.AdminComponentVO;

import java.util.List;

public interface AdminComponentService {
    Result<List<AdminComponentVO>> getComponent();

    Result<List<Variant>> getVariant(int id);
}
