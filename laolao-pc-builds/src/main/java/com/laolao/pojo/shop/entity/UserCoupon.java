package com.laolao.pojo.shop.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserCoupon {
    /**
     * 用户持有的优惠券主键Id
     */
    private Integer id;

    /**
     * 用户 ID
     */
    private Integer userId;

    /**
     * 优惠券 ID
     */
    private Integer couponId;

    /**
     * 使用的订单 ID
     */
    private Integer orderId;

    /**
     * 状态: 0 - 未使用，1 - 已使用，2 - 已过期
     */
    private Integer status;

    /**
     * 领取时间
     */
    private LocalDateTime claimedAt;


    /**
     * 使用时间
     */
    private LocalDateTime usedAt;
}
