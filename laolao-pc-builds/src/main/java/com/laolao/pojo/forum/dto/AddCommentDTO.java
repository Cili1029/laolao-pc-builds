package com.laolao.pojo.forum.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddCommentDTO {
    /**
     * 帖子Id
     */
    private int id;

    /**
     * 内容
     */
    private String content;
}
