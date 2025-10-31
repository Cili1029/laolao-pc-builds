package com.laolao.service.forum.impl;

import com.laolao.common.context.BaseContext;
import com.laolao.common.result.Result;
import com.laolao.mapper.forum.CommentMapper;
import com.laolao.mapper.forum.LikeMapper;
import com.laolao.mapper.forum.PostMapper;
import com.laolao.pojo.forum.dto.LikeDTO;
import com.laolao.pojo.forum.entity.Like;
import com.laolao.service.forum.LikeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {
    @Resource
    private LikeMapper likeMapper;
    @Resource
    private PostMapper postMapper;
    @Resource
    private CommentMapper commentMapper;

    @Override
    public Result<Integer> like(LikeDTO likeDTO) {
        int userId = BaseContext.getCurrentId();
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
            return Result.success(1,"点赞成功");
        } else {
            // 更新状态：1->0 或 0->1
            int newStatus = 1 - currentStatus;
            likeMapper.updateStatus(userId, likeDTO.getLikeType(),
                    likeDTO.getLikeId(), newStatus);
            updateLikeCount(likeDTO, newStatus == 1 ? 1 : -1);
            return Result.success(newStatus == 1 ? 1 : -1,newStatus == 1 ? "点赞！" : "取消点赞！");
        }
    }

    private void updateLikeCount(LikeDTO likeDTO, int delta) {
        if (likeDTO.getLikeType() == 1) {
            postMapper.updateLikeCount(likeDTO.getLikeId(), delta);
        } else {
            commentMapper.updateLikeCount(likeDTO.getLikeId(), delta);
        }
    }
}
