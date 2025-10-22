package com.laolao.pojo.shop.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CancelDTO {
    /**
     * 订单号
     */
    private String number;

    /**
     * 取消原因
     */
    private String cancelReason;
}

