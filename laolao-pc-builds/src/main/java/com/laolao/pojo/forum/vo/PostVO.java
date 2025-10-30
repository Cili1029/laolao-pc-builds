package com.laolao.pojo.forum.vo;

import com.laolao.pojo.user.vo.UserVO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostVO {
    /**
     * 帖子Id
     */
    private int id;

    /**
     * 发帖用户
     */
    private UserVO user;

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
     * 是否已经点赞
     * 1-点了 2-没点
     */
    private int like;

    /**
     * 评论数
     */
    private Integer commentCount;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 评论
     */
    private List<CommentVO> comment;

    /**
     * 回复数
     */
    private int replyCount;
}
