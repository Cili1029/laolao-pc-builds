package com.laolao.pojo.user.Listener;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class LikeNotification {
    /**
     * 接收通知的用户ID
     */
    private int receiverId;

    /**
     * 发送通知的用户ID（0-系统通知）
     */
    private int senderId;

    /**
     * 类型：1-系统通知，2-评论帖子，3-回复评论，4-点赞
     */
    private int type;

    /**
     * 所属帖子id
     */
    private Integer postId;

    /**
     * 所属评论id
     */
    private Integer commentId;

    /**
     * 通知标题
     */
    private String title;

    /**
     * 通知简要内容（冗余存储，用于展示）
     */
    private String content;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 点赞类型：1-帖子，2-评论
     */
    private int likeType;

    /**
     * 点赞Id
     */
    private int likeId;
}
