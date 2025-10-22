package com.laolao.pojo.shop.vo;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrdersVO {

    /**
     * 订单号
     */
    private String number;

    /**
     * 订单状态: 1待付款 2待发货 3已发货 4已完成 5已取消 6退款中 7已退款
     */
    private Integer status;

    /**
     * 商品名（选一个商品显示）
     */
    private String name;

    /**
     * 商品数量
     */
    private int productCount;

    /**
     * 订单图片
     */
    private String image;

    /**
     * 实付金额
     */
    private BigDecimal finalAmount;
}
