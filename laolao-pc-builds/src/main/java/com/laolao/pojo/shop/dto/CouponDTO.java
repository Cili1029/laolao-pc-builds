package com.laolao.pojo.shop.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CouponDTO {
    /**
     * 订单号
     */
    private String number;

    /**
     * 用户优惠券Id
     */
    private int id;

    /**
     * 用户Id
     */
    private int userId;

    /**
     * 状态
     */
    private int status;

    /**
     * 使用时间
     */
    private LocalDateTime usedAt;
}
