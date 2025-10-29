package com.laolao.pojo.shop.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddressDTO {
    /**
     * 主键
     */
    private int id;

    /**
     * 订单号
     */
    private String number;

    /**
     * 用户Id
     */
    private int userId;

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
}
