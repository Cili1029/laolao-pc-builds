package com.laolao.common.constant;

// 订单状态定义
public class OrderConstant {
    // 待付款
    public static final int PENDING_PAYMENT = 1;
    // 待发货
    public static final int PENDING_SHIPMENT = 2;
    // 已发货/物流中
    public static final int SHIPPED = 3;
    // 待收货
    public static final int PENDING_RECEIPT = 4;
    // 已完成
    public static final int COMPLETED = 5;
    // 已取消
    public static final int CANCELLED = 6;
}
