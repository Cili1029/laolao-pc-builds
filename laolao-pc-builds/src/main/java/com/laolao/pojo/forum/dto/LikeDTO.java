package com.laolao.pojo.forum.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LikeDTO {
    /**
     * 点赞类型 (1:帖子, 2:评论和回复)
     */
    private int likeType;

    /**
     * 被点赞的Id
     */
    private int likeId;
}
