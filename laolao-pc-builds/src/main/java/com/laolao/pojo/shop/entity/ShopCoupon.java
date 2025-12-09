package com.laolao.pojo.shop.entity;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ShopCoupon {
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
     * 库存
     */
    private Integer stock;

    /**
     * 状态: 0 - 禁用，1 - 启用
     */
    private Integer status;

    /**
     * 有效期开始
     */
    private LocalDateTime validStartTime;

    /**
     * 有效期结束
     */
    private LocalDateTime validEndTime;

    /**
     * 创建人
     */
    private int createdBy;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 最后更新人
     */
    private int updatedBy;

    /**
     * 最后更新时间
     */
    private LocalDateTime updatedAt;

    /**
     * 已领取
     * 联表用
     */
    private int obtained;
}
