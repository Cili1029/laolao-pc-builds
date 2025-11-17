package com.laolao.pojo.shop.vo;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserCouponVO {
    /**
     * 用户持有的优惠券主键Id
     */
    private Integer id;

    /**
     * 优惠券 ID
     */
    private Integer couponId;

    /**
     * 优惠券名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 最低使用金额
     */
    private BigDecimal minimumAmount;

    /**
     * 抵扣金额
     */
    private BigDecimal discountAmount;

    /**
     * 有效期结束
     */
    private LocalDateTime validEndTime;

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
