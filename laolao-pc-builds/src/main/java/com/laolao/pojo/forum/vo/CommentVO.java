package com.laolao.pojo.forum.vo;

import com.laolao.pojo.user.vo.UserVO;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentVO {
    /**
     * 评论Id
     */
    private int id;

    /**
     * 帖子Id
     */
    private int postId;

    /**
     * 用户
     */
    private UserVO user;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 图片
     */
    private String image;

    /**
     * 点赞数
     */
    private int likeCount;

    /**
     * 是否已经点赞
     * 1-点了 0-没点
     */
    private int like = 0;

    /**
     * 回复数
     */
    private int replyCount;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}
