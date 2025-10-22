package com.laolao.pojo.shop.dto;

import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CartProductDTO implements Serializable {
    /**
     * 商品类型
     */
    private int productType;

    /**
     * 商品Id
     */
    private int productId;
}
