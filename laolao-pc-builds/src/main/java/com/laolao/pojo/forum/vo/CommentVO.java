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
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 评论的回复
     */
    private List<CommentReplyVO> reply;
}
