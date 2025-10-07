package com.laolao.pojo.vo;

import java.math.BigDecimal;

public class ProductsVO {
    private int id;
    private String name;
    private String image;
    private String commonDescription;
    private BigDecimal price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCommonDescription() {
        return commonDescription;
    }

    public void setCommonDescription(String commonDescription) {
        this.commonDescription = commonDescription;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ProductsVO(int id, String name, String image, String commonDescription, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.commonDescription = commonDescription;
        this.price = price;
    }

    public ProductsVO() {}

    @Override
    public String toString() {
        return "ProductsVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", commonDescription='" + commonDescription + '\'' +
                ", price=" + price +
                '}';
    }
}
