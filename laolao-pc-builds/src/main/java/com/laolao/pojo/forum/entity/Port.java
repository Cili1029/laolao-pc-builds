package com.laolao.pojo.forum.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Port {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 发帖用户Id
     */
    private Integer userId;

    /**
     * 所属分类Id
     */
    private Integer categoryId;

    /**
     * 帖子标题
     */
    private String title;

    /**
     * 帖子内容
     */
    private String content;

    /**
     * 帖子图片
     */
    private String images;

    /**
     * 浏览数
     */
    private Integer viewCount;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 评论数
     */
    private Integer commentCount;

    /**
     * 状态：1正常，2删除
     */
    private Integer status;

    /**
     * 是否置顶
     */
    private Integer top;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}
