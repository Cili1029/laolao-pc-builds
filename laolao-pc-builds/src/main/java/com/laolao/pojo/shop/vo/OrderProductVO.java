package com.laolao.pojo.shop.vo;

import java.math.BigDecimal;

public class OrderProductVO {
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
     * 数量
     */
    private Integer quantity;

    /**
     * 单价
     */
    private BigDecimal price;

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

    public OrderProductVO() {
    }

    public OrderProductVO(String name, String variantName, String image, Integer quantity, BigDecimal price) {
        this.name = name;
        this.variantName = variantName;
        this.image = image;
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderProductVO{" +
                "name='" + name + '\'' +
                ", variantName='" + variantName + '\'' +
                ", image='" + image + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
