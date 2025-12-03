package com.laolao.pojo.shop.vo;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminAddVariantVO implements Serializable {
    /**
     * 所属部件Id
     */
    private int componentId;

    /**
     * 版本名
     */
    private String variantName;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 描述
     */
    private String description;

    /**
     * 库存
     */
    private int stock;
}
