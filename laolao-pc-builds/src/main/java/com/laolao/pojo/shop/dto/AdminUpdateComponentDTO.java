package com.laolao.pojo.shop.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminUpdateComponentDTO {
    /**
     * 主键
     */
    private int id;

    /**
     * 组件名
     */
    private String name;

    /**
     * 商品类型
     */
    private int categoryId;

    /**
     * 图片
     */
    private List<String> images;

    /**
     * 通用描述
     */
    private String commonDescription;

    /**
     * 排序权重
     */
    private int sort;
}
