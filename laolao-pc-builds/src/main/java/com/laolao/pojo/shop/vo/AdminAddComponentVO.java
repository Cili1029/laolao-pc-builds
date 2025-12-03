package com.laolao.pojo.shop.vo;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminAddComponentVO {
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
     * 创建人
     */
    private int createdBy;
}
