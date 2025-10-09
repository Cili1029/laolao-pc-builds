package com.laolao.service.impl;

import com.laolao.mapper.ComponentMapper;
import com.laolao.pojo.vo.ComponentVariantVO;
import com.laolao.pojo.vo.ComponentVO;
import com.laolao.common.result.Result;
import com.laolao.service.ComponentService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComponentServiceImpl implements ComponentService {

    @Resource
    private ComponentMapper componentMapper;

    @Override
    public Result<List<ComponentVO>> listWithCategoryId(int categoryId) {
        List<ComponentVO> componentVoList =  componentMapper.getByConditions(categoryId, null);
        return Result.success(componentVoList);
    }

    @Override
    public Result<List<ComponentVariantVO>> listWithComponentId(int componentId) {
        List<ComponentVariantVO> componentVariantVO =  componentMapper.getByComponentsId(componentId);
        return Result.success(componentVariantVO);
    }

    @Override
    public Result<List<ComponentVO>> searchByName(int categoryId, String searchContent) {
        List<ComponentVO> componentVoList =  componentMapper.getByConditions(categoryId, searchContent);
        return Result.success(componentVoList);
    }
}
