package com.laolao.pojo.vo;

import java.math.BigDecimal;

public class ProductVO {
    private int id;
    private int type;
    private String name;
    private BigDecimal price;
    private String image;
    private String commonDescription;
    private String description;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductVO(int id, int type, String name, BigDecimal price, String image, String commonDescription, String description) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.price = price;
        this.image = image;
        this.commonDescription = commonDescription;
        this.description = description;
    }

    public ProductVO() {
    }

    @Override
    public String toString() {
        return "ComponentVO{" +
                "id=" + id +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", commonDescription='" + commonDescription + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
