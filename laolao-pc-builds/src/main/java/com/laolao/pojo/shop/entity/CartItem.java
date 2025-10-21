package com.laolao.pojo.shop.entity;

import java.time.LocalDateTime;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public CartItem() {
    }

    public CartItem(int id, int userId, int productType, int productId, int quantity, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.productType = productType;
        this.productId = productId;
        this.quantity = quantity;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", userId=" + userId +
                ", productType=" + productType +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", createdAt=" + createdAt +
                '}';
    }
}
