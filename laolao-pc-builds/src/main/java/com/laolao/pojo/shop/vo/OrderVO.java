package com.laolao.pojo.shop.vo;

import com.laolao.pojo.shop.entity.OrderDetail;

import java.math.BigDecimal;
import java.util.List;

public class OrderVO {
    /**
     * 商品原金额
     */
    private BigDecimal originalAmount;

    /**
     * 商品优惠金额
     */
    private BigDecimal discountAmount;

    /**
     * 商品信息
     */
    List<OrderDetail> orderDetails;

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public OrderVO() {
    }

    public OrderVO(BigDecimal originalAmount, BigDecimal discountAmount, List<OrderDetail> orderDetails) {
        this.originalAmount = originalAmount;
        this.discountAmount = discountAmount;
        this.orderDetails = orderDetails;
    }

    @Override
    public String toString() {
        return "OrderVO{" +
                "originalAmount=" + originalAmount +
                ", discountAmount=" + discountAmount +
                ", orderDetails=" + orderDetails +
                '}';
    }
}
