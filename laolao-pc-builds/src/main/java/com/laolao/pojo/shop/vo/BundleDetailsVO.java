package com.laolao.pojo.shop.vo;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BundleDetailsVO implements Serializable {
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
    private List<String> images;

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
     * 所包含的变种信息
     */
    private List<VariantVO> variants;
}
