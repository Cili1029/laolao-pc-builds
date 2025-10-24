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
     * 用户
     */
    private UserVO user;

    /**
     * 回复给哪个用户ID,0则为直接评论
     */
    private Integer replyToUserId;

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
     * 创建时间
     */
    private LocalDateTime createdAt;
}
