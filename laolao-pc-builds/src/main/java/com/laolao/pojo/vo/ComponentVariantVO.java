package com.laolao.pojo.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class ComponentVariantVO implements Serializable {
    private  int id;
    private int componentId;
    private String variantName;
    private BigDecimal price;
    private String description;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ComponentVariantVO(int id, int componentId, String variantName, BigDecimal price, String description) {
        this.id = id;
        this.componentId = componentId;
        this.variantName = variantName;
        this.price = price;
        this.description = description;
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
                '}';
    }
}
