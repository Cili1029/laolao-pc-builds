package com.laolao.pojo.shop.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class ComponentVariantVO implements Serializable {
    /**
     * 商品版本Id
     */
    private int id;

    /**
     * 商品Id
     */
    private int componentId;

    /**
     * 版本名
     */
    private String variantName;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 描述
     */
    private String description;

    // 主机用
    /**
     * 商品名
     */
    private String name;

    /**
     * 图片
     */
    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getComponentId() {
        return componentId;
    }

    public void setComponentId(int componentId) {
        this.componentId = componentId;
    }

    public String getVariantName() {
        return variantName;
    }

    public void setVariantName(String variantName) {
        this.variantName = variantName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public ComponentVariantVO(int id, int componentId, String variantName, BigDecimal price, String description, String name, String image) {
        this.id = id;
        this.componentId = componentId;
        this.variantName = variantName;
        this.price = price;
        this.description = description;
        this.name = name;
        this.image = image;
    }

    public ComponentVariantVO() {
    }

    @Override
    public String toString() {
        return "ComponentVariantVO{" +
                "id=" + id +
                ", componentId=" + componentId +
                ", variantName='" + variantName + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
