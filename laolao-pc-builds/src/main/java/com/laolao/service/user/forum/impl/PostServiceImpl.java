package com.laolao.service.user.forum.impl;

import com.laolao.common.context.UserContext;
import com.laolao.common.result.Result;
import com.laolao.converter.MapStruct;
import com.laolao.mapper.common.SysFileMapper;
import com.laolao.mapper.user.forum.CommentMapper;
import com.laolao.mapper.user.forum.LikeMapper;
import com.laolao.mapper.user.forum.PostMapper;
import com.laolao.mapper.common.UserCommonMapper;
import com.laolao.pojo.forum.dto.CreatePostDTO;
import com.laolao.pojo.forum.dto.LikeTarget;
import com.laolao.pojo.forum.entity.Comment;
import com.laolao.pojo.forum.entity.Like;
import com.laolao.pojo.forum.entity.Post;
import com.laolao.pojo.forum.vo.*;
import com.laolao.pojo.user.entity.User;
import com.laolao.pojo.user.vo.UserSimpleVO;
import com.laolao.service.user.forum.PostService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Resource
    private PostMapper postMapper;
    @Resource
    private MapStruct mapStruct;
    @Resource
    private UserCommonMapper userCommonMapper;
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private LikeMapper likeMapper;
    @Resource
    private SysFileMapper sysFileMapper;

    @Override
    public Result<List<PostSimpleVO>> getPostSimple(int categoryId) {
        List<Post> postList;
        if (categoryId == 5) {
            postList = postMapper.getHot(0);
        } else {
            postList = postMapper.selectPostSimple(categoryId);
        }
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

        // 获取所有这个帖子评论(直接评论)
        List<Comment> allCommentList = commentMapper.selectComment(id);
        // 获取所有评论的用户信息(set去重，看可能会有用户重复评论)
        Set<Integer> allUserIdSet = allCommentList.stream().map(Comment::getUserId).collect(Collectors.toCollection(LinkedHashSet::new));
        // 将贴主放进去
        allUserIdSet.add(post.getUserId());
        List<User> allUserList = userCommonMapper.selectUser(new ArrayList<>(allUserIdSet));
        // 将用户转为id为key的索引
        Map<Integer, User> userMap = allUserList.stream().collect(Collectors.toMap(User::getId, user -> user));
        // 获取贴主
        UserSimpleVO poster = mapStruct.userToUserSimpleVO(userMap.get(post.getUserId()));
        // 写入帖子VO
        postVO.setUser(poster);

        // 判断是否点赞
        ArrayList<LikeTarget> likeTargets = new ArrayList<>();
        likeTargets.add(new LikeTarget(1, id));

        // 评论
        List<CommentVO> commentVOList = new ArrayList<>();
        if (allCommentList.isEmpty()) {
            // 无评论
            List<Like> likes = likeMapper.queryLike(UserContext.getCurrentId(), likeTargets);
            if (!likes.isEmpty()) {
                // 点赞了
                postVO.setLike(1);
            }
            return Result.success(postVO);
        } else {
            // 有，设置用户信息并转换写入

            // 帖子
            likeTargets.addAll(allCommentList.stream().map(comment -> new LikeTarget(2, comment.getId())).collect(Collectors.toCollection(ArrayList::new)));

            List<Like> likes = likeMapper.queryLike(UserContext.getCurrentId(), likeTargets);
            //转为Map
            Map<String, Like> likeMap = likes.stream().collect(Collectors.toMap(
                    like -> like.getLikeType() + "_" + like.getLikeId(),
                    like -> like
            ));

            for (Comment comment : allCommentList) {
                // 获取对应用户
                User user = userMap.get(comment.getUserId());
                // 转换
                UserSimpleVO userSimpleVO = mapStruct.userToUserSimpleVO(user);
                // 写入
                comment.setUser(userSimpleVO);
                CommentVO commentVO = mapStruct.commentToCommentVO(comment);

                if (likeMap.get("2_" + commentVO.getId()) != null) {
                    // 点赞了
                    commentVO.setLike(1);
                }

                if (likeMap.get("1_" + id) != null) {
                    // 点赞了
                    postVO.setLike(1);
                }
                commentVOList.add(commentVO);
            }
            // 至此直接评论完成
        }
        postVO.setComment(commentVOList);
        return Result.success(postVO);
    }

    public Result<List<CommentReplyVO>> getReply(int id) {
        // 获得所有回复
        List<Comment> replyList = commentMapper.selectReply(id);
        // 获取所有评论的用户信息(set去重，看可能会有用户重复评论)
        Set<Integer> allUserIdSet = replyList.stream().map(Comment::getUserId).collect(Collectors.toCollection(LinkedHashSet::new));
        List<User> allUserList = userCommonMapper.selectUser(new ArrayList<>(allUserIdSet));
        // 将用户转为id为key的索引
        Map<Integer, User> userMap = allUserList.stream().collect(Collectors.toMap(User::getId, user -> user));
        // 写入头像
        List<CommentReplyVO> commentReplyVOList = new ArrayList<>();

        // 获取点赞信息
        ArrayList<LikeTarget> likeTargets = replyList.stream().map(reply -> new LikeTarget(2, reply.getId())).collect(Collectors.toCollection(ArrayList::new));
        List<Like> likes = likeMapper.queryLike(UserContext.getCurrentId(), likeTargets);
        //转为Map
        Map<Integer, Like> likeMap = likes.stream().collect(Collectors.toMap(
                Like::getLikeId,
                like -> like
        ));

        for (Comment reply : replyList) {
            User user = userMap.get(reply.getUserId());
            UserSimpleVO userSimpleVO = mapStruct.userToUserSimpleVO(user);
            reply.setUser(userSimpleVO);
            CommentReplyVO commentReplyVO = mapStruct.commentToCommentReplyVO(reply);
            if (likeMap.get(commentReplyVO.getId()) != null) {
                // 点赞了
                commentReplyVO.setLike(1);
            }
            commentReplyVOList.add(commentReplyVO);
        }
        return Result.success(commentReplyVOList);
    }

    @Override
    public Result<List<PostSimpleVO>> getHot(int count) {
        List<Post> postList = postMapper.getHot(count);
        List<PostSimpleVO> postSimpleVOList = new ArrayList<>();
        for (Post post : postList) {
            PostSimpleVO postSimpleVO = mapStruct.PostToSimpleVO(post);
            postSimpleVOList.add(postSimpleVO);
        }
        return Result.success(postSimpleVOList);
    }

    @Override
    public Result<List<PostSimpleVO>> search(int categoryId, String searchContent) {

        List<Post> postList = postMapper.searchPostSimple(categoryId, searchContent);
        if (postList.isEmpty()) {
            return Result.success(Collections.emptyList(), "什么也没找到...");
        }
        List<PostSimpleVO> postSimpleVOList = new ArrayList<>();
        for (Post post : postList) {
            PostSimpleVO postSimpleVO = mapStruct.PostToSimpleVO(post);
            postSimpleVOList.add(postSimpleVO);
        }
        return Result.success(postSimpleVOList, "找到东西了！");
    }

    @Override
    public Result<PostSimpleVO> createPost(CreatePostDTO createPostDTO) {
        Post post = Post.builder()
                .userId(UserContext.getCurrentId())
                .categoryId(createPostDTO.getCategoryId())
                .title(createPostDTO.getTitle())
                .content(createPostDTO.getContent())
                .images(createPostDTO.getImages())
                .build();
        if (!CollectionUtils.isEmpty(createPostDTO.getImages())) {
            post.setImages(createPostDTO.getImages());
            sysFileMapper.update(post.getImages());
        }
        postMapper.insertPost(post);
        PostSimpleVO postSimpleVO = mapStruct.PostToSimpleVO(post);
        return Result.success(postSimpleVO, "发布成功");
    }

    @Override
    @Transactional
    public Result<String> deletePost(int id) {
        int userId = UserContext.getCurrentId();
        // 先删评论
        commentMapper.deleteCommentByPostId(id, userId);
        // 删帖子
        postMapper.delete(id, userId);
        return Result.success("删除成功");
    }
}
