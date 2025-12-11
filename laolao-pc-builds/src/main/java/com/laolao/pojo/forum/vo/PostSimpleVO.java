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
     * 最后评论人
     */
    private int commentedBy;

    /**
     * 最后评论时间
     */
    private LocalDateTime commentedAt;

    /**
     * 热度分数
     */
    private int hotScore;
}
