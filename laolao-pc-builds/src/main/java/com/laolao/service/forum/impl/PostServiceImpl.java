package com.laolao.service.forum.impl;

import com.laolao.common.result.Result;
import com.laolao.converter.MapStruct;
import com.laolao.mapper.forum.CommentMapper;
import com.laolao.mapper.forum.PostMapper;
import com.laolao.mapper.user.UserMapper;
import com.laolao.pojo.forum.entity.Comment;
import com.laolao.pojo.forum.entity.Post;
import com.laolao.pojo.forum.vo.CommentReplyVO;
import com.laolao.pojo.forum.vo.CommentVO;
import com.laolao.pojo.forum.vo.PostSimpleVO;
import com.laolao.pojo.forum.vo.PostVO;
import com.laolao.pojo.user.entity.User;
import com.laolao.pojo.user.vo.UserVO;
import com.laolao.service.forum.PostService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Resource
    private PostMapper postMapper;
    @Resource
    private MapStruct mapStruct;
    @Resource
    private UserMapper userMapper;
    @Resource
    private CommentMapper commentMapper;

    @Override
    public Result<List<PostSimpleVO>> getPostSimple(int categoryId) {
        List<Post> postList = postMapper.selectPostSimple(categoryId);
        List<PostSimpleVO> postSimpleVOList = new ArrayList<>();
        for (Post post : postList) {
            PostSimpleVO postSimpleVO = mapStruct.PostToSimpleVO(post);
            postSimpleVOList.add(postSimpleVO);
        }
        return Result.success(postSimpleVOList);
    }


    @Override
    public Result<PostVO> getPost(int id) {
        // 先获取帖子基本信息
        Post post = postMapper.selectPost(id);
        PostVO postVO = mapStruct.PostToPostVO(post);

        // 获取所有这个帖子评论
        List<Comment> allCommentList = commentMapper.selectComment(id);
        // 获取所有评论的用户信息(set去重，看可能会有用户重复评论)
        Set<Integer> allUserIdSet = allCommentList.stream().map(Comment::getUserId).collect(Collectors.toCollection(LinkedHashSet::new));
        // 将贴主放进去
        allUserIdSet.add(post.getUserId());
        List<User> allUserList = userMapper.selectUser(new ArrayList<>(allUserIdSet));
        // 将要用户转为id为key的索引
        Map<Integer, User> userMap = allUserList.stream().collect(Collectors.toMap(User::getId, user -> user));
        // 获取贴主
        UserVO userVO = mapStruct.userToUserVO(userMap.get(post.getUserId()));
        // 写入帖子VO
        postVO.setUser(userVO);

        // 将帖子分为直接回复和回复的回复
        Map<Boolean, List<Comment>> collect = allCommentList.stream().collect(Collectors.partitioningBy(comment -> comment.getReplyToUserId() == 0));

        // 直接评论
        List<Comment> commentList = collect.get(true);
        List<CommentVO> commentVOList = new ArrayList<>();
        // 无评论
        if (commentList.isEmpty()) {
            return Result.success(postVO);
        } else {
            // 有，设置用户信息并转换写入
            setUser(userMap, commentList, commentVOList, null, 1);
            // 至此直接评论完成
        }

        // 判断是否有评论的评论
        List<Comment> replyList = collect.get(false);
        if (replyList.isEmpty()) {
            //没有,只写入直接评论返回
            postVO.setComment(commentVOList);
            return Result.success(postVO);
        }
        // 有评论 查询用户信息
        List<CommentReplyVO> replyVOList = new ArrayList<>();
        setUser(userMap, replyList, null, replyVOList, 2);
        // 至此评论的评论完成

        // 将id根据回复对象进行分组
        Map<Integer, List<CommentReplyVO>> replyGroup = replyVOList.stream().collect(Collectors.groupingBy(CommentReplyVO::getReplyToUserId));
        // 根据id配对合并
        for (CommentVO commentVO : commentVOList) {
            // 没有就null
            List<CommentReplyVO> list = replyGroup.getOrDefault(commentVO.getUser().getId(), Collections.emptyList());
            // 写入
            commentVO.setReply(list);
        }
        // 写入
        postVO.setComment(commentVOList);
        return Result.success(postVO);
    }

    // 用于设置评论的用户信息
    private void setUser(Map<Integer, User> userMap, List<Comment> commentList, List<CommentVO> commentVOList, List<CommentReplyVO> replyVOList, int type) {
        // 遍历，写入
        for (Comment comment : commentList) {
            // 获取对应用户
            User user = userMap.get(comment.getUserId());
            // 转换
            UserVO userVO = mapStruct.userToUserVO(user);
            // 写入
            comment.setUser(userVO);
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
