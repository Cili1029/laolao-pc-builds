package com.laolao.pojo.shop.vo;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ComponentVariantVO implements Serializable {
    /**
     * 商品版本Id
     */
    private int id;

    /**
     * 商品Id
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

    // 主机用
    /**
     * 商品名
     */
    private String name;

    /**
     * 图片
     */
    private String image;
}
