package com.laolao.pojo.shop.vo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ShopCategoryVO {
    /**
     * 主键
     */
    private int id;

    /**
     * 类别类型
     */
    private int productType;

    /**
     * 类别名
     */
    private String name;

    /**
     * 类别图片
     */
    private String image;
}
