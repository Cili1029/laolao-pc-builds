package com.laolao.pojo.shop.entity;

import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDetail {
    /**
     * 主键
     */
    private Integer id;

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
     * 订单id
     */
    private Integer orderId;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 单价
     */
    private BigDecimal price;
}
