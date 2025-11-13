package com.laolao.pojo.forum.dto;

import lombok.*;

import java.util.List;

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

    /**
     * 图片
     */
    private List<String> images;
}
