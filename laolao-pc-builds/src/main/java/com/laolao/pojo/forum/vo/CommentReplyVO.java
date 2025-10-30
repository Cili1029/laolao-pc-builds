package com.laolao.pojo.forum.vo;

import com.laolao.pojo.user.vo.UserVO;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentReplyVO {
    /**
     * 帖子Id
     */
    private int id;

    /**
     * 用户
     */
    private UserVO user;

    /**
     * 回复指向的评论Id,0则为直接评论
     */
    private Integer parent;

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
    private Integer likeCount;

    /**
     * 是否已经点赞
     * 1-点了 2-没点
     */
    private int like;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}
