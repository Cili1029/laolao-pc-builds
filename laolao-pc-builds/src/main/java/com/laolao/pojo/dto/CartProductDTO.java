package com.laolao.pojo.dto;

import java.io.Serializable;

public class CartProductDTO implements Serializable {
    /**
     * 商品类型
     */
    private int type;

    /**
     * 商品Id
     */
    private int productId;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public CartProductDTO() {
    }

    public CartProductDTO(int type, int id) {
        this.type = type;
        this.productId = id;
    }

    @Override
    public String toString() {
        return "CartProductDTO{" +
                "type=" + type +
                ", productId=" + productId +
                '}';
    }
}
