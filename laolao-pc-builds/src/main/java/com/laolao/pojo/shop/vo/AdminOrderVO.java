package com.laolao.pojo.shop.vo;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminOrderVO {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 订单号
     */
    private String number;

    /**
     * 收货人
     */
    private String consignee;

    /**
     * 收货人手机号
     */
    private String phone;

    /**
     * 收货地址（用于存入固定订单信息）
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
}
