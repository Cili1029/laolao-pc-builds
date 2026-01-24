package com.laolao.pojo.shop.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PayDTO {
    /**
     * 订单号
     */
    private String number;

    /**
     * 标题
     */
    private String subject;

    /**
     * 付款类型
     */
    private int payType;
}
