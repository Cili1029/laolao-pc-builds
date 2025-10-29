package com.laolao.pojo.shop.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ChangeOrderAddressDTO implements Serializable {
    /**
     * 收货人地址主键
     */
    private int addressId;

    /**
     * 订单号
     */
    private String number;
}
