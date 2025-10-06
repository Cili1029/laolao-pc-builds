package com.laolao.service.impl;

import com.laolao.converter.MapStruct;
import com.laolao.mapper.CategoryMapper;
import com.laolao.pojo.entity.Category;
import com.laolao.pojo.vo.CategoryVO;
import com.laolao.result.Result;
import com.laolao.service.CategoryService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private MapStruct mapStruct;

    @Override
    public Result<List<CategoryVO>> list() {
        List<Category> categoryList = categoryMapper.get();

        List<CategoryVO> categoryVOList = new ArrayList<>();
        for (Category category : categoryList) {
            CategoryVO categoryVO = mapStruct.categoryToCategoryVO(category);
            categoryVOList.add(categoryVO);
        }
        return Result.success(categoryVOList);
    }
}
