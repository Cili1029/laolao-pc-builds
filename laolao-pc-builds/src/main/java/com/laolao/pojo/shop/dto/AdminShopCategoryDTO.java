package com.laolao.pojo.shop.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminShopCategoryDTO {
    /**
     * 主键
     */
    private int id;

    /**
     * 商品类型
     */
    private int productType;

    /**
     * 类型名
     */
    private String name;

    /**
     * 类别图片
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
