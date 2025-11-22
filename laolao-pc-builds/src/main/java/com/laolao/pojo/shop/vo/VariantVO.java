package com.laolao.pojo.shop.vo;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VariantVO {
    /**
     * 主键
     */
    private int id;

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
    private String Description;

    /**
     * 库存
     */
    private int stock;

    // 主机用
    /**
     * 数量
     */
    private int quantity;

    /**
     * 产品名
     */
    private String name;

    /**
     * 图片
     */
    private String image;
}
