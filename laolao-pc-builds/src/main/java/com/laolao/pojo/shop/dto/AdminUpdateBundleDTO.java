package com.laolao.pojo.shop.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminUpdateBundleDTO {
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
     * 描述
     */
    private String description;

    /**
     * 价格
     */
    private int price;

    /**
     * 排序权重
     */
    private int sort;
}
