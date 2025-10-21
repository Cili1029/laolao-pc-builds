package com.laolao.pojo.shop.vo;

import java.math.BigDecimal;

public class OrdersVO {

    /**
     * 订单号
     */
    private String number;

    /**
     * 订单状态: 1待付款 2待发货 3已发货 4已完成 5已取消 6退款中 7已退款
     */
    private Integer status;

    /**
     * 商品名（选一个商品显示）
     */
    private String name;

    /**
     * 商品数量
     */
    private int productCount;

    /**
     * 订单图片
     */
    private String image;

    /**
     * 实付金额
     */
    private BigDecimal finalAmount;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public BigDecimal getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(BigDecimal finalAmount) {
        this.finalAmount = finalAmount;
    }

    public OrdersVO() {
    }

    public OrdersVO(String number, Integer status, String name, int productCount, String image, BigDecimal finalAmount) {
        this.number = number;
        this.status = status;
        this.name = name;
        this.productCount = productCount;
        this.image = image;
        this.finalAmount = finalAmount;
    }

    @Override
    public String toString() {
        return "OrdersVO{" +
                "number='" + number + '\'' +
                ", status=" + status +
                ", name='" + name + '\'' +
                ", productCount=" + productCount +
                ", image='" + image + '\'' +
                ", finalAmount=" + finalAmount +
                '}';
    }
}
