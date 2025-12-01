package com.laolao.pojo.forum.dto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminForumCategoryDTO {
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

    /**
     * 图片
     */
    private String image;

    /**
     * 排序
     */
    private int sort;

    /**
     * 创建人
     */
    private int createdBy;
}
