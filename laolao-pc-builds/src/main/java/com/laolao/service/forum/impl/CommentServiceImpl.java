package com.laolao.service.forum.impl;

import com.laolao.common.context.BaseContext;
import com.laolao.common.result.Result;
import com.laolao.mapper.forum.CommentMapper;
import com.laolao.mapper.user.UserMapper;
import com.laolao.pojo.forum.dto.AddCommentDTO;
import com.laolao.pojo.forum.dto.AddReplyDTO;
import com.laolao.pojo.forum.entity.Comment;
import com.laolao.pojo.forum.vo.CommentReplyVO;
import com.laolao.pojo.forum.vo.CommentVO;
import com.laolao.pojo.user.entity.User;
import com.laolao.service.forum.CommentService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CommentServiceImpl implements CommentService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private PostServiceImpl postService;

    @Override
    public Result<CommentVO> addComment(AddCommentDTO addCommentDTO) {
        int userId = BaseContext.getCurrentId();
        User user = userMapper.getUser(userId);
        Map<Integer, User> userMap = new HashMap<>();
        userMap.put(userId, user);
        // 构建评论
        Comment comment = Comment.builder()
                .postId(addCommentDTO.getId())
                .userId(userId)
                .content(addCommentDTO.getContent())
                .createdAt(LocalDateTime.now())
                .build();
        // 写入数据库
        commentMapper.insertComment(comment);
        List<CommentVO> commentVOList = new ArrayList<>();
        postService.setUserToComment(userMap, Collections.singletonList(comment), commentVOList, null, 1);
        return Result.success(commentVOList.get(0), "发表成功！");
    }

    @Override
    public Result<CommentReplyVO> addReply(AddReplyDTO addReplyDTO) {
        int userId = BaseContext.getCurrentId();
        User user = userMapper.getUser(userId);
        Map<Integer, User> userMap = new HashMap<>();
        userMap.put(userId, user);
        // 构建评论
        Comment comment = Comment.builder()
                .postId(addReplyDTO.getId())
                .userId(userId)
                .parent(addReplyDTO.getParent())
                .content(addReplyDTO.getContent())
                .createdAt(LocalDateTime.now())
                .build();
        // 写入数据库
        commentMapper.insertReply(comment);

        List<CommentReplyVO> commentReplyVOList = new ArrayList<>();
        postService.setUserToComment(userMap, Collections.singletonList(comment), null, commentReplyVOList, 2);
        return Result.success(commentReplyVOList.get(0), "发表成功！");
    }

    @Override
    @Transactional
    public Result<String> deleteComment(int id) {
        int userId = BaseContext.getCurrentId();
        // 先删除主评论
        int res = commentMapper.deleteComment(id, userId);
        if (res == 0) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.error("删除失败：评论不存在或无权限");
        }
        // 删除该评论下的所有回复
        commentMapper.deleteReplyByParent(id);
        return Result.success("删除成功");
    }

    @Override
    public Result<String> deleteReply(int id) {
        int userId = BaseContext.getCurrentId();
        commentMapper.deleteComment(id, userId);
        return null;
    }
}
