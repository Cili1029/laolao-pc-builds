package com.laolao.pojo.shop.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminAddBundleDTO {
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
    private String description;

    /**
     * 通用描述
     */
    private String price;
}
