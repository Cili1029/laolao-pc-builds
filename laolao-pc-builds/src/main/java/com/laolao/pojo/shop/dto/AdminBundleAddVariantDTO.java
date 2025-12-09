package com.laolao.pojo.shop.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminBundleAddVariantDTO {
    /**
     * 整机Id
     */
    private int bundleId;

    /**
     * 版本Id
     */
    private int variantId;

    /**
     * 数量
     */
    private int quantity;

    /**
     * 创建人
     */
    private int createdBy;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 最后更新人
     */
    private int updatedBy;

    /**
     * 最后更新时间
     */
    private LocalDateTime updatedAt;
}
