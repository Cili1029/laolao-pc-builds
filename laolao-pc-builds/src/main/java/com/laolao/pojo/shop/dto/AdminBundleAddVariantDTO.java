package com.laolao.pojo.shop.dto;

import lombok.*;

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
}
