package com.laolao.pojo.vo;

import java.math.BigDecimal;

public class ProductVO {
    /**
     * 主键
     */
    private int id;

    /**
     * 商品类型
     */
    private int productType;

    /**
     * 商品名
     */
    private String name;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 图片
     */
    private String image;

    /**
     * 通用描述
     */
    private String commonDescription;

    /**
     * 描述
     */
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
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

    public ProductVO(int id, int productType, String name, BigDecimal price, String image, String commonDescription, String description) {
        this.id = id;
        this.productType = productType;
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
                ", productType=" + productType +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", commonDescription='" + commonDescription + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
