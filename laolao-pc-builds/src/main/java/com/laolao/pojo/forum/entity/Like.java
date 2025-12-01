package com.laolao.pojo.forum.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Like {
    /**
     * 主键Id
     */
    private int id;

    /**
     * 用户Id
     */
    private int userId;

    /**
     * 点赞类型 (1:帖子, 2:评论和回复)
     */
    private int likeType;

    /**
     * 被点赞的Id
     */
    private int likeId;

    /**
     * 状态 (1: 点赞, 0: 取消)
     */
    private int status;

    /**
     * 最后更新时间（点赞，取消点赞时间）
     */
    private LocalDateTime updatedAt;
}
