package com.laolao.pojo.entity;

import java.math.BigDecimal;

public class OrderDetail {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 商品名
     */
    private String name;

    /**
     * 商品版本
     */
    private String variantName;

    /**
     * 图片
     */
    private String image;

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 单价
     */
    private BigDecimal price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVariantName() {
        return variantName;
    }

    public void setVariantName(String variantName) {
        this.variantName = variantName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public OrderDetail() {
    }

    public OrderDetail(Integer id, String name, String variantName, String image, Integer orderId, Integer quantity, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.variantName = variantName;
        this.image = image;
        this.orderId = orderId;
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", variantName='" + variantName + '\'' +
                ", image='" + image + '\'' +
                ", orderId=" + orderId +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
