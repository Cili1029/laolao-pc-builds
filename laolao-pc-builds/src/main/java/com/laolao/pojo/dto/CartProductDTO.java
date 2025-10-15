package com.laolao.pojo.dto;

import java.io.Serializable;

public class CartProductDTO implements Serializable {
    /**
     * 商品类型
     */
    private int productType;

    /**
     * 商品Id
     */
    private int productId;

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public CartProductDTO() {
    }

    public CartProductDTO(int productType, int id) {
        this.productType = productType;
        this.productId = id;
    }

    @Override
    public String toString() {
        return "CartProductDTO{" +
                "productType=" + productType +
                ", productId=" + productId +
                '}';
    }
}
