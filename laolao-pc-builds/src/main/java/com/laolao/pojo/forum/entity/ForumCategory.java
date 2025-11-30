package com.laolao.pojo.forum.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ForumCategory {
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
     * 状态：1启用，0禁用
     */
    private int status;

    /**
     * 创建人
     */
    private int createdBy;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}
