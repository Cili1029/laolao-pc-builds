package com.laolao.pojo.shop.vo;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class AdminBundleVariantVO{
    /**
     * 主键
     */
    private int id;

    /**
     * 版本Id
     */
    private int variantId;

    /**
     * 数量
     */
    private int quantity;

    /**
     * 逐渐名
     */
    private String name;

    /**
     * 版本名
     */
    private String variantName;

    /**
     * 价格
     */
    private BigDecimal price;
}
