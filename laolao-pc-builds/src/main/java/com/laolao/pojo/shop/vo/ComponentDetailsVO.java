package com.laolao.pojo.shop.vo;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ComponentDetailsVO {
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
    private String image;

    /**
     * 通用描述
     */
    private String commonDescription;

    /**
     * 销售额
     */
    private int sales;

    /**
     * 所包含的变种信息
     */
    private List<VariantVO> variants;
}
