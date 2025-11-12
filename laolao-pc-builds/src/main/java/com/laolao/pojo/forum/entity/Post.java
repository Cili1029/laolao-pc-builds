package com.laolao.pojo.forum.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Post {
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
    private List<String> images;

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

    /**
     * 最后更新时间（最后评论）
     */
    private LocalDateTime updatedAt;
}
