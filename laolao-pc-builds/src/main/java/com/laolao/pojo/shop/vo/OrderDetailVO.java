package com.laolao.pojo.shop.vo;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDetailVO {
    /**
     * 订单号
     */
    private String number;

    /**
     * 订单状态: 1待付款 2待发货 3已发货 4已完成 5已取消 6退款中 7已退款
     */
    private Integer status;

    /**
     * 包含的商品信息
     */
    private List<OrderProductVO> products;

    /**
     * 商品原金额
     */
    private BigDecimal originalAmount;

    /**
     * 商品优惠金额
     */
    private BigDecimal discountAmount;

    /**
     * 收货人
     */
    private String consignee;

    /**
     * 收货人手机号
     */
    private String phone;

    /**
     * 收货地址
     */
    private String address;

    /**
     * 订单取消原因
     */
    private String cancelReason;

    /**
     * 订单拒绝原因
     */
    private String rejectionReason;

    /**
     * 支付时间
     */
    private LocalDateTime checkoutTime;

    /**
     * 订单取消时间
     */
    private LocalDateTime cancelTime;

    /**
     * 发货时间
     */
    private LocalDateTime deliveryTime;

    /**
     * 确认收货时间
     */
    private LocalDateTime receiveTime;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}
