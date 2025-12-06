package com.laolao.pojo.common;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StockOrQuantityDTO {
    /**
     * 主键
     */
    private int id;

    /**
     * 库存
     */
    private int stock;

    /**
     * 数量
     */
    private int quantity;
}
