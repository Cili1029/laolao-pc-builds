package com.laolao.pojo.shop.entity;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Component implements Serializable {
    /**
     * 主键
     */
    private int id;

    /**
     * 组件名
     */
    private String name;

    /**
     * 商品类型
     */
    private int categoryId;

    /**
     * 图片
     */
    private List<String> images;

    /**
     * 通用描述
     */
    private String commonDescription;

    /**
     * 销售额
     */
    private int sales;

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
