package com.laolao.pojo.shop.dto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BuyProductDTO {
    /**
     * 商品类型
     */
    private int productType;

    /**
     * 商品Id
     */
    private int productId;

    /**
     * 数量
     */
    private int quantity;
}
