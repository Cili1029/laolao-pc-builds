package com.laolao.pojo.shop.vo;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductVO {
    /**
     * 主键
     */
    private int id;

    /**
     * 商品类型
     */
    private int productType;

    /**
     * 商品名
     */
    private String name;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 图片
     */
    private String image;

    /**
     * 通用描述
     */
    private String commonDescription;

    /**
     * 描述
     */
    private String description;
}
