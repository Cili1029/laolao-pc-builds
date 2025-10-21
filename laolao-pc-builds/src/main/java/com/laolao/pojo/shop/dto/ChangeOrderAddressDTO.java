package com.laolao.pojo.shop.dto;

import java.io.Serializable;

public class ChangeOrderAddressDTO implements Serializable {
    /**
     * 收货人地址主键
     */
    private int addressId;

    /**
     * 订单号
     */
    private String number;

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public ChangeOrderAddressDTO() {
    }

    public ChangeOrderAddressDTO(int addressId, String number) {
        this.addressId = addressId;
        this.number = number;
    }

    @Override
    public String toString() {
        return "ChangeOrderAddressDTO{" +
                "addressId=" + addressId +
                ", number='" + number + '\'' +
                '}';
    }
}
