package com.laolao.service.admin.forum.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.laolao.common.result.Result;
import com.laolao.mapper.admin.forum.AdminCommentMapper;
import com.laolao.mapper.admin.forum.AdminPostMapper;
import com.laolao.pojo.forum.entity.Post;
import com.laolao.service.admin.forum.AdminPostService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminPostServiceImpl implements AdminPostService {
    @Resource
    private AdminPostMapper adminPostMapper;
    @Resource
    private AdminCommentMapper adminCommentMapper;

    @Override
    public Result<PageInfo<Post>> getPost(Integer pageNum, Integer pageSize, String searchContent) {
        PageHelper.startPage(pageNum, pageSize);
        List<Post> postList = StringUtils.isNotBlank(searchContent)
                ? adminPostMapper.search(searchContent)
                : adminPostMapper.select();
        PageInfo<Post> pageInfo = new PageInfo<>(postList);
        return Result.success(pageInfo);
    }

    @Override
    public Result<String> updateCategory(Integer id, int categoryId) {
        Post post = Post.builder().id(id).categoryId(categoryId).build();
        adminPostMapper.update(post);
        return Result.success("修改成功！");
    }

    @Override
    public Result<String> deletePost(Integer id) {
        // 先删评论
        adminCommentMapper.deleteCommentByPostId(id);
        // 删帖子
        adminPostMapper.delete(id);
        return Result.success("删除成功");
    }

    @Override
    public Result<String> changeStatus(Integer id, int status) {
        Post post = Post.builder().id(id).status(status).build();
        adminCommentMapper.updateStatus(id, status);
        adminPostMapper.update(post);
        return Result.success(status == 1 ? "已启用！" : "已禁用！");
    }
}
