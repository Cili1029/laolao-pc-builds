package com.laolao.service.user.forum.impl;

import com.laolao.common.result.Result;
import com.laolao.converter.MapStruct;
import com.laolao.mapper.user.forum.ForumCategoryMapper;
import com.laolao.pojo.forum.vo.ForumCategoryVO;
import com.laolao.pojo.forum.entity.ForumCategory;
import com.laolao.service.user.forum.ForumCategoryService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ForumCategoryServiceImpl implements ForumCategoryService {
    @Resource
    private ForumCategoryMapper forumCategoryMapper;
    @Resource
    private MapStruct mapStruct;

    @Override
    public Result<List<ForumCategoryVO>> list() {
        List<ForumCategory> forumCategoryList = forumCategoryMapper.get();

        List<ForumCategoryVO> forumCategoryVOList = new ArrayList<>();
        for (ForumCategory forumCategory : forumCategoryList) {
            ForumCategoryVO forumCategoryVO = mapStruct.ForumCategoryToCategoryVO(forumCategory);
            forumCategoryVOList.add(forumCategoryVO);
        }
        return Result.success(forumCategoryVOList);
    }
}
