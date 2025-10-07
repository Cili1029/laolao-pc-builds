package com.laolao.pojo.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductsEditionVO implements Serializable {
    private int productId;
    private String editionName;
    private BigDecimal price;
    private String description;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getEditionName() {
        return editionName;
    }

    public void setEditionName(String editionName) {
        this.editionName = editionName;
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

    public ProductsEditionVO(int productId, String editionName, BigDecimal price, String description) {
        this.productId = productId;
        this.editionName = editionName;
        this.price = price;
        this.description = description;
    }

    public ProductsEditionVO() {
    }

    @Override
    public String toString() {
        return "ProductsEditionVO{" +
                "productId=" + productId +
                ", editionName='" + editionName + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }
}
