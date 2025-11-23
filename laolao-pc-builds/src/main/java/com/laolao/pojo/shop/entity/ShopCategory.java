package com.laolao.pojo.shop.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ShopCategory {
    /**
     * 主键
     */
    private int id;

    /**
     * 商品类型
     */
    private int productType;

    /**
     * 类型名
     */
    private String name;

    /**
     * 类别图片
     */
    private String image;

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
}
