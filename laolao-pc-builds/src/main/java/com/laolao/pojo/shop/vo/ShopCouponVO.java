package com.laolao.pojo.shop.vo;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ShopCouponVO {
    /**
     * 优惠券 Id
     */
    private Integer id;

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
     * 有效期开始
     */
    private LocalDateTime validStartTime;

    /**
     * 有效期结束
     */
    private LocalDateTime validEndTime;
}
