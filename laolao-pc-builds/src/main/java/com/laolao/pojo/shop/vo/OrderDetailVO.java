package com.laolao.pojo.shop.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDetailVO {
    /**
     * 订单号
     */
    private String number;

    /**
     * 订单状态: 1待付款 2待发货 3已发货 4已完成 5已取消 6退款中 7已退款
     */
    private Integer status;

    /**
     * 包含的商品信息
     */
    private List<OrderProductVO> products;

    /**
     * 商品原金额
     */
    private BigDecimal originalAmount;

    /**
     * 商品优惠金额
     */
    private BigDecimal discountAmount;

    /**
     * 收货人
     */
    private String consignee;

    /**
     * 收货人手机号
     */
    private String phone;

    /**
     * 收货地址
     */
    private String address;

    /**
     * 订单取消原因
     */
    private String cancelReason;

    /**
     * 订单拒绝原因
     */
    private String rejectionReason;

    /**
     * 支付时间
     */
    private LocalDateTime checkoutTime;

    /**
     * 订单取消时间
     */
    private LocalDateTime cancelTime;

    /**
     * 发货时间
     */
    private LocalDateTime deliveryTime;

    /**
     * 确认收货时间
     */
    private LocalDateTime receiveTime;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<OrderProductVO> getProducts() {
        return products;
    }

    public void setProducts(List<OrderProductVO> products) {
        this.products = products;
    }

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

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public LocalDateTime getCheckoutTime() {
        return checkoutTime;
    }

    public void setCheckoutTime(LocalDateTime checkoutTime) {
        this.checkoutTime = checkoutTime;
    }

    public LocalDateTime getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(LocalDateTime cancelTime) {
        this.cancelTime = cancelTime;
    }

    public LocalDateTime getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(LocalDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public LocalDateTime getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(LocalDateTime receiveTime) {
        this.receiveTime = receiveTime;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getNumber() {
        return number;
    }

    public OrderDetailVO(String number, Integer status, List<OrderProductVO> products, BigDecimal originalAmount, BigDecimal discountAmount, String consignee, String phone, String address, String cancelReason, String rejectionReason, LocalDateTime checkoutTime, LocalDateTime cancelTime, LocalDateTime deliveryTime, LocalDateTime receiveTime, LocalDateTime createdAt) {
        this.number = number;
        this.status = status;
        this.products = products;
        this.originalAmount = originalAmount;
        this.discountAmount = discountAmount;
        this.consignee = consignee;
        this.phone = phone;
        this.address = address;
        this.cancelReason = cancelReason;
        this.rejectionReason = rejectionReason;
        this.checkoutTime = checkoutTime;
        this.cancelTime = cancelTime;
        this.deliveryTime = deliveryTime;
        this.receiveTime = receiveTime;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "OrderDetailVO{" +
                "number='" + number + '\'' +
                ", status=" + status +
                ", products=" + products +
                ", originalAmount=" + originalAmount +
                ", discountAmount=" + discountAmount +
                ", consignee='" + consignee + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", cancelReason='" + cancelReason + '\'' +
                ", rejectionReason='" + rejectionReason + '\'' +
                ", checkoutTime=" + checkoutTime +
                ", cancelTime=" + cancelTime +
                ", deliveryTime=" + deliveryTime +
                ", receiveTime=" + receiveTime +
                ", createdAt=" + createdAt +
                '}';
    }
}
