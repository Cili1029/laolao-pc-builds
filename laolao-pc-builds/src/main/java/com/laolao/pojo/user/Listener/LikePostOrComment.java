package com.laolao.pojo.user.Listener;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LikePostOrComment {
    private int userId;
    private String content;
    private Integer postId;
}
