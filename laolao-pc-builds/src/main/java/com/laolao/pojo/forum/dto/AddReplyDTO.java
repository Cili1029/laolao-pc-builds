package com.laolao.pojo.forum.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddReplyDTO {
    /**
     * 帖子Id
     */
    private int id;

    /**
     * 回复指向的评论Id
     */
    private int parent;

    /**
     * 内容
     */
    private String content;
}
