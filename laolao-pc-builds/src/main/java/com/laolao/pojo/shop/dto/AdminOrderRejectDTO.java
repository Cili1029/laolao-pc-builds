package com.laolao.pojo.shop.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminOrderRejectDTO {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 订单拒绝原因
     */
    private String rejectionReason;

    /**
     * 订单拒绝时间
     */
    private LocalDateTime cancelTime;
}
