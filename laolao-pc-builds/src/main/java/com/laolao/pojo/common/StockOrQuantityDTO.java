package com.laolao.pojo.common;

import lombok.*;

import java.time.LocalDateTime;

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

    /**
     * 最后更新人
     */
    private int updatedBy;

    /**
     * 最后更新时间
     */
    private LocalDateTime updatedAt;

}
