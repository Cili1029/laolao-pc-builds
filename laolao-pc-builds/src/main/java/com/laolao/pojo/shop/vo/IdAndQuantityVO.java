package com.laolao.pojo.shop.vo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class IdAndQuantityVO {
    /**
     * 商品Id
     */
    private int productId;

    /**
     * 数量
     */
    private int quantity;
}
