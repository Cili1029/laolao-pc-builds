package com.laolao.pojo.forum.vo;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ForumCategoryVO {
    /**
     * 主键
     */
    private int id;

    /**
     * 分类名
     */
    private String name;

    /**
     * 分类描述
     */
    private String description;
}
