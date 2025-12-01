package com.laolao.pojo.shop.entity;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Variant implements Serializable {
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

    /**
     * 状态 1启用 0停用
     */
    private Integer status;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}
