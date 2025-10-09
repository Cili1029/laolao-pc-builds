package com.laolao.service;

import com.laolao.pojo.vo.CategoryVO;
import com.laolao.common.result.Result;

import java.util.List;

public interface CategoryService {
    Result<List<CategoryVO>> list();
}
