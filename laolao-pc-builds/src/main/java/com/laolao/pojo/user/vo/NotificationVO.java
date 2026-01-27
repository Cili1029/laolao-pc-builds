package com.laolao.pojo.user.vo;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class NotificationVO {
    /**
     * 主键
     */
    private int id;

    /**
     * 类型：1-系统通知，2-评论帖子，3-回复评论，4-点赞
     */
    private int type;

    /**
     * 所属帖子id
     */
    private Integer postId;

    /**
     * 所属分类id
     */
    private Integer categoryId;

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
}
