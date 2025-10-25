package com.laolao.pojo.forum.entity;

import com.laolao.pojo.user.vo.UserVO;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Comment {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 关联帖子ID
     */
    private Integer postId;

    /**
     * 评论用户ID
     */
    private Integer userId;

    /**
     * 回复指向的评论Id,0则为直接评论
     */
    private Integer parent;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论图片，最多一张
     */
    private String image;

    /**
     * 状态：1正常，2删除
     */
    private Integer status;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;


    // 封装VO用
    /**
     * 用户
     */
    private UserVO user;
}
