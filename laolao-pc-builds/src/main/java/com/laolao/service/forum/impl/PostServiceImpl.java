package com.laolao.service.forum.impl;

import com.laolao.common.result.Result;
import com.laolao.converter.MapStruct;
import com.laolao.mapper.forum.PostMapper;
import com.laolao.pojo.forum.entity.Post;
import com.laolao.pojo.forum.vo.PostSimpleVO;
import com.laolao.service.forum.PostService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Resource
    private PostMapper postMapper;
    @Resource
    private MapStruct mapStruct;

    @Override
    public Result<List<PostSimpleVO>> getPostSimple(int categoryId) {
        List<Post> postList = postMapper.selectPost(categoryId);
        List<PostSimpleVO> postSimpleVOList = new ArrayList<>();
        for (Post post : postList) {
            PostSimpleVO postSimpleVO = mapStruct.PostToSimpleVO(post);
            postSimpleVOList.add(postSimpleVO);
        }
        return Result.success(postSimpleVOList);
    }
}
