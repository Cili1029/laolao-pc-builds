package com.laolao.service.user.forum.impl;

import com.laolao.common.context.UserContext;
import com.laolao.common.result.Result;
import com.laolao.converter.MapStruct;
import com.laolao.mapper.common.SysFileMapper;
import com.laolao.mapper.user.forum.CommentMapper;
import com.laolao.mapper.user.forum.PostMapper;
import com.laolao.mapper.common.UserCommonMapper;
import com.laolao.pojo.forum.dto.AddCommentDTO;
import com.laolao.pojo.forum.dto.AddReplyDTO;
import com.laolao.pojo.forum.entity.Comment;
import com.laolao.pojo.forum.vo.CommentReplyVO;
import com.laolao.pojo.forum.vo.CommentVO;
import com.laolao.pojo.user.entity.User;
import com.laolao.pojo.user.vo.UserSimpleVO;
import com.laolao.service.user.forum.CommentService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CommentServiceImpl implements CommentService {
    @Resource
    private UserCommonMapper userCommonMapper;
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private MapStruct mapStruct;
    @Resource
    private PostMapper postMapper;
    @Resource
    private SysFileMapper sysFileMapper;

    @Override
    public Result<CommentVO> addComment(AddCommentDTO addCommentDTO) {
        int userId = UserContext.getCurrentId();
        User user = userCommonMapper.getUser(userId);
        Map<Integer, User> userMap = new HashMap<>();
        userMap.put(userId, user);
        // 构建评论
        Comment comment = Comment.builder()
                .postId(addCommentDTO.getId())
                .userId(userId)
                .content(addCommentDTO.getContent())
                .images(addCommentDTO.getImages())
                .build();
        // 修改文件状态
        if (!CollectionUtils.isEmpty(addCommentDTO.getImages())) {
            comment.setImages(addCommentDTO.getImages());
            sysFileMapper.update(addCommentDTO.getImages());
        }
        // 写入数据库
        commentMapper.insertComment(comment);
        postMapper.updateCommentCount(addCommentDTO.getId(), 1, LocalDateTime.now(), UserContext.getCurrentId());
        List<CommentVO> commentVOList = new ArrayList<>();
        setUserToComment(userMap, Collections.singletonList(comment), commentVOList, null, 1);
        return Result.success(commentVOList.get(0), "发表成功！");
    }

    @Override
    public Result<CommentReplyVO> addReply(AddReplyDTO addReplyDTO) {
        int userId = UserContext.getCurrentId();
        User user = userCommonMapper.getUser(userId);
        Map<Integer, User> userMap = new HashMap<>();
        userMap.put(userId, user);
        // 构建评论
        Comment comment = Comment.builder()
                .postId(addReplyDTO.getId())
                .userId(userId)
                .parent(addReplyDTO.getParent())
                .content(addReplyDTO.getContent())
                .images(addReplyDTO.getImages())
                .build();
        // 修改文件状态
        if (!CollectionUtils.isEmpty(addReplyDTO.getImages())) {
            comment.setImages(addReplyDTO.getImages());
            sysFileMapper.update(addReplyDTO.getImages());
        }
        // 写入数据库
        commentMapper.insertReply(comment);
        postMapper.updateCommentCount(addReplyDTO.getId(), 1, LocalDateTime.now(), UserContext.getCurrentId());
        List<CommentReplyVO> commentReplyVOList = new ArrayList<>();
        setUserToComment(userMap, Collections.singletonList(comment), null, commentReplyVOList, 2);
        return Result.success(commentReplyVOList.get(0), "发表成功！");
    }

    @Override
    @Transactional
    public Result<String> deleteComment(int postId, int commentId) {
        // 先删除主评论
        int res = commentMapper.deleteComment(commentId);
        if (res == 0) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.error("删除失败：评论不存在或无权限");
        }
        // 删除该评论下的所有回复
        int delta = commentMapper.deleteReplyByParent(commentId);
        if (delta > 0) {
            postMapper.updateCommentCount(postId, -(1 + delta), null, null);
        } else {
            postMapper.updateCommentCount(postId, -1, null, null);
        }
        return Result.success("删除成功");
    }

    @Override
    public Result<String> deleteReply(int postId, int id) {
        commentMapper.deleteComment(id);
        postMapper.updateCommentCount(postId, -1, null, null);
        return Result.success("删除成功");
    }

    // 用于设置评论的用户信息
    void setUserToComment(Map<Integer, User> userMap, List<Comment> commentList, List<CommentVO> commentVOList, List<CommentReplyVO> replyVOList, int type) {
        // 遍历，写入
        for (Comment comment : commentList) {
            // 获取对应用户
            User user = userMap.get(comment.getUserId());
            // 转换
            UserSimpleVO userSimpleVO = mapStruct.userToUserSimpleVO(user);
            // 写入
            comment.setUser(userSimpleVO);
            // 分为直接评论和评论的评论
            if (type == 1) {
                CommentVO commentVO = mapStruct.commentToCommentVO(comment);
                commentVOList.add(commentVO);
            } else {
                CommentReplyVO commentReplyVO = mapStruct.commentToCommentReplyVO(comment);
                replyVOList.add(commentReplyVO);
            }
        }
    }
}
