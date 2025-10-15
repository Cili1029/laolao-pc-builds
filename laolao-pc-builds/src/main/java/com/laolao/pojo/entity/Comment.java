package com.laolao.pojo.entity;

import java.time.LocalDateTime;

public class Comment {
    /**
     * 主键
     */
    private int id;

    /**
     * 评论用户
     */
    private int userId;

    /**
     * 评论
     */
    private String comment;

    /**
     * 评论层级
     */
    private int level;

    /**
     * 父级评论Id
     */
    private int parentId;

    /**
     * 所属商品类型
     */
    private int productType;

    /**
     * 商品Id
     */
    private int productId;

    /**
     * 创建时间
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Comment() {
    }

    public Comment(int id, int userId, String comment, int level, int parentId, int productType, int productId, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.comment = comment;
        this.level = level;
        this.parentId = parentId;
        this.productType = productType;
        this.productId = productId;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "ProductComment{" +
                "id=" + id +
                ", userId=" + userId +
                ", comment='" + comment + '\'' +
                ", level=" + level +
                ", parentId=" + parentId +
                ", productType=" + productType +
                ", productId=" + productId +
                ", createdAt=" + createdAt +
                '}';
    }
}
