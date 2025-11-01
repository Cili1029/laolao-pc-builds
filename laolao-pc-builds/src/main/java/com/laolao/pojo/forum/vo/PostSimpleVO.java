package com.laolao.pojo.forum.vo;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostSimpleVO {
    /**
     * 主键
     */
    private int id;

    /**
     * 帖子标题
     */
    private String title;

    /**
     * 点赞数
     */
    private int likeCount;

    /**
     * 评论数
     */
    private int commentCount;

    /**
     * 最后更新时间（最后评论）
     */
    private LocalDateTime updatedAt;
}
