package com.laolao.pojo.shop.vo;


public class ShopCategoryVO {
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

    public ShopCategoryVO(int id, int productType, String name) {
        this.id = id;
        this.productType = productType;
        this.name = name;
    }

    public ShopCategoryVO() {
    }

    @Override
    public String toString() {
        return "CategoryVO{" +
                "id=" + id +
                ", productType=" + productType +
                ", name='" + name + '\'' +
                '}';
    }
}
