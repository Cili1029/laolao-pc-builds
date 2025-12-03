package com.laolao.pojo.shop.vo;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminUpdateVariantVO implements Serializable {
    /**
     * 版本Id
     */
    private int id;

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
}
