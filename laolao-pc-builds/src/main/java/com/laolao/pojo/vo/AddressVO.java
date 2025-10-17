package com.laolao.pojo.vo;

import java.io.Serializable;

public class AddressVO implements Serializable {
    /**
     * 主键
     */
    private int id;
    /**
     * 收货人
     */
    private String consignee;
    /**
     * 电话
     */
    private String phone;
    /**
     * 省份
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区县
     */
    private String district;
    /**
     * 详细地址
     */
    private String detailAddress;
    /**
     * 是否为默认
     */
    private int isDefault;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    public AddressVO() {
    }

    public AddressVO(int id, String consignee, String phone, String province, String city, String district, String detailAddress, int isDefault) {
        this.id = id;
        this.consignee = consignee;
        this.phone = phone;
        this.province = province;
        this.city = city;
        this.district = district;
        this.detailAddress = detailAddress;
        this.isDefault = isDefault;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", consignee='" + consignee + '\'' +
                ", phone='" + phone + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", detailAddress='" + detailAddress + '\'' +
                ", isDefault=" + isDefault +
                '}';
    }
}
