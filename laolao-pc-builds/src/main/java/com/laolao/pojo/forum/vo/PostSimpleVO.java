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
     * 所属分类Id
     */
    private Integer categoryId;

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

    /**
     * 热度分数
     */
    private int hotScore;
}
