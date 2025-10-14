package com.laolao.pojo.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class CartProductVO implements Serializable {
    /**
     * 商品Id
     */
    private int id;

    /**
     * 商品类型
     */
    private int type;

    /**
     * 商品名
     */
    private String name;

    /**
     * 版本名
     */
    private String variantName;

    /**
     * 图片
     */
    private String image;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 数量
     */
    private int quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public CartProductVO() {
    }

    public CartProductVO(int id, int type, String name, String variantName, String image, BigDecimal price, int quantity) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.variantName = variantName;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartProductVO{" +
                "id=" + id +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", variantName='" + variantName + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
