package com.laolao.service;

import com.laolao.pojo.vo.ComponentVariantVO;
import com.laolao.pojo.vo.ComponentVO;
import com.laolao.common.result.Result;

import java.util.List;

public interface ComponentService {
    Result<List<ComponentVO>> listWithCategoryId(int categoryId);

    Result<List<ComponentVariantVO>> listWithComponentId(int componentId);

    Result<List<ComponentVO>> searchByName(int categoryId, String searchContent);
}
