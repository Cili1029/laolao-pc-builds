package com.laolao.pojo.shop.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminOrderShipDTO {
    /**
     * 主键
     */
    private int id;

    /**
     * 订单状态: 1待付款 2待发货 3已发货 4已完成 5已取消 6退款中 7已退款
     */
    private Integer status;

    /**
     * 快递单号
     */
    private String trackingNo;

    /**
     * 发货时间
     */
    private LocalDateTime shipTime;
}
