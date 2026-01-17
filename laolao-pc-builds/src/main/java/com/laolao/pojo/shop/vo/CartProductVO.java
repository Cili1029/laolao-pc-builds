package com.laolao.pojo.shop.vo;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CartProductVO {
    /**
     * 商品Id
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
     * 版本名
     */
    private String variantName;

    /**
     * 图片
     */
    private String image;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 数量
     */
    private int quantity;
}
