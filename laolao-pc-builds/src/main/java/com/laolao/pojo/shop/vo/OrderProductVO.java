package com.laolao.pojo.shop.vo;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderProductVO {
    /**
     * 商品名
     */
    private String name;

    /**
     * 商品版本
     */
    private String variantName;

    /**
     * 图片
     */
    private String image;

    /**
     * 数量
     */
    private int quantity;

    /**
     * 单价
     */
    private BigDecimal price;
}
