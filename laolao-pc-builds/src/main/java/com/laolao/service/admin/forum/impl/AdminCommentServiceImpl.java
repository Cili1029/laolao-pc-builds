package com.laolao.service.admin.forum.impl;

import com.laolao.common.result.Result;
import com.laolao.mapper.admin.forum.AdminCommentMapper;
import com.laolao.service.admin.forum.AdminCommentService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class AdminCommentServiceImpl implements AdminCommentService {
    @Resource
    private AdminCommentMapper adminCommentMapper;

    @Override
    public Result<String> deleteComment(Integer type ,Integer id) {
        if (type == 1) {
            // 先删回复
            adminCommentMapper.deleteReplyByParentId(id);
        }
        // 删评论
        adminCommentMapper.deleteById(id);
        return Result.success("删除成功");
    }
}
