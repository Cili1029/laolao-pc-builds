package com.laolao.pojo.forum.dto;

import com.laolao.pojo.forum.vo.CommentVO;
import com.laolao.pojo.user.vo.UserVO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreatePostDTO {

    /**
     * 所属分类Id
     */
    private int categoryId;

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
}
