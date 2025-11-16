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
public class Bundle implements Serializable {
    /**
     * 主键
     */
    private int id;

    /**
     * 整机名
     */
    private String name;

    /**
     * 所属分类Id
     */
    private int categoryId;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 图片
     */
    private String image;

    /**
     * 描述
     */
    private String Description;

    /**
     * 销售额
     */
    private int sales;

    /**
     * 库存
     */
    private int stock;

    /**
     * 状态 1启用 0停用
     */
    private Integer status;

    /**
     * 排序
     */
    private int sort;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 最后更新人
     */
    private String updatedBy;

    /**
     * 最后更新时间
     */
    private LocalDateTime updatedAt;

    // 联表查询用
    /**
     * 商品类型
     */
    private int productType;
}
