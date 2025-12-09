package com.laolao.pojo.shop.entity;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CartItem {
    /**
     * 主键
     */
    private int id;

    /**
     * 用户Id
     */
    private int userId;

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

    /**
     * 添加时间
     */
    private LocalDateTime createdAt;

    /**
     * 最后更新时间
     */
    private LocalDateTime updatedAt;
}
