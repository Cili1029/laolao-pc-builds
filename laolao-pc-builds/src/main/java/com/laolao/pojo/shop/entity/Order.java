package com.laolao.pojo.shop.entity;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Order {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 订单号
     */
    private String number;

    /**
     * 订单状态: 1待付款 2待发货 3物流中 4已完成 5已取消 6退款中 7已退款
     */
    private Integer status;

    /**
     * 下单用户id
     */
    private Integer userId;

    /**
     * 商品原金额
     */
    private BigDecimal originalAmount;

    /**
     * 商品优惠金额
     */
    private BigDecimal discountAmount;

    /**
     * 使用的优惠券Id
     */
    private Integer userCouponId;

    /**
     * 收货人
     */
    private String consignee;

    /**
     * 收货人手机号
     */
    private String phone;

    /**
     * 当前选择的地址Id
     */
    private int addressId;

    /**
     * 收货地址（用于存入固定订单信息）
     */
    private String address;

    /**
     * 快递单号
     */
    private String trackingNo;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 支付时间
     */
    private LocalDateTime checkoutTime;

    /**
     * 发货时间
     */
    private LocalDateTime shipTime;

    /**
     * 到货时间
     */
    private LocalDateTime arrivalTime;

    /**
     * 确认收货时间
     */
    private LocalDateTime receiveTime;

    /**
     * 订单取消时间
     */
    private LocalDateTime cancelTime;

    /**
     * 订单取消原因
     */
    private String cancelReason;

    /**
     * 订单拒绝原因
     */
    private String rejectionReason;
}
