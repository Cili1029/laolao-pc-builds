package com.laolao.pojo.shop.vo;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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
    private int defaultAddress;
}
