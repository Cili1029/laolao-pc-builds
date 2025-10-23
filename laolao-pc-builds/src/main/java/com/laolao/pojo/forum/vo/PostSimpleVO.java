package com.laolao.pojo.forum.vo;

import lombok.*;

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
     * 浏览数
     */
    private int viewCount;

    /**
     * 点赞数
     */
    private int likeCount;

    /**
     * 评论数
     */
    private int commentCount;
}
