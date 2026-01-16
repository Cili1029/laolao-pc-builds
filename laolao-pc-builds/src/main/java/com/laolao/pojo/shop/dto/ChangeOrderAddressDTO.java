package com.laolao.pojo.shop.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ChangeOrderAddressDTO {
    /**
     * 收货人地址主键
     */
    private int addressId;

    /**
     * 订单号
     */
    private String number;
}
