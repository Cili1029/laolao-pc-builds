package com.laolao.service.user.forum.impl;

import com.laolao.common.constant.StatusConstant;
import com.laolao.common.constant.ForumLikeConstant;
import com.laolao.common.context.UserContext;
import com.laolao.common.result.Result;
import com.laolao.mapper.user.forum.CommentMapper;
import com.laolao.mapper.user.forum.LikeMapper;
import com.laolao.mapper.user.forum.PostMapper;
import com.laolao.pojo.forum.dto.LikeDTO;
import com.laolao.pojo.forum.entity.Like;
import com.laolao.pojo.user.Listener.CommentNotification;
import com.laolao.pojo.user.Listener.LikeNotification;
import com.laolao.service.user.forum.LikeService;
import jakarta.annotation.Resource;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeServiceImpl implements LikeService {
    @Resource
    private LikeMapper likeMapper;
    @Resource
    private PostMapper postMapper;
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public Result<Integer> like(LikeDTO likeDTO) {
        int userId = UserContext.getCurrentId();
        // 先查询当前状态
        Integer currentStatus = likeMapper.getStatus(userId, likeDTO.getLikeType(), likeDTO.getLikeId());
        if (currentStatus == null) {
            // 首次点赞
            Like like = Like.builder()
                    .userId(userId)
                    .likeType(likeDTO.getLikeType())
                    .likeId(likeDTO.getLikeId())
                    .build();
            likeMapper.like(like);
            updateLikeCount(likeDTO, 1);
            setEvent(userId, likeDTO.getLikeType(), likeDTO.getLikeId());
            return Result.success(1,"点赞成功");
        } else {
            // 更新状态：1->0 或 0->1
            int newStatus = 1 - currentStatus;
            likeMapper.updateStatus(userId, likeDTO.getLikeType(),
                    likeDTO.getLikeId(), newStatus);
            updateLikeCount(likeDTO, newStatus == StatusConstant.LIKED ? 1 : -1);

            if (newStatus == StatusConstant.LIKED) {
                setEvent(userId, likeDTO.getLikeType(), likeDTO.getLikeId());
            }

            return Result.success(newStatus == StatusConstant.LIKED ? 1 : -1, newStatus == StatusConstant.LIKED ? "点赞！" : "取消点赞！");
        }
    }

    private void updateLikeCount(LikeDTO likeDTO, int delta) {
        if (likeDTO.getLikeType() == ForumLikeConstant.POST) {
            postMapper.updateLikeCount(likeDTO.getLikeId(), delta);
        } else {
            commentMapper.updateLikeCount(likeDTO.getLikeId(), delta);
        }
    }

    private void setEvent(int userId, int likeType, int likeId) {
        // 发布异步通知
        LikeNotification commentNotification = LikeNotification.builder()
                .senderId(userId)
                .likeType(likeType)
                .likeId(likeId)
                .build();
        eventPublisher.publishEvent(commentNotification);
    }
}
