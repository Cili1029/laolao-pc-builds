package com.laolao.pojo.shop.dto;

import lombok.*;

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
}
