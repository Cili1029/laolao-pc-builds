package com.laolao.pojo.dto;

import java.io.Serializable;

public class ChangeOrderAddressDTO implements Serializable {
    /**
     * 收货人地址主键
     */
    private int addressId;

    /**
     * 订单Id
     */
    private int orderId;

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
