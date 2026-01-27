package com.laolao.pojo.user.Listener;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderNumberAndUser {
    /**
     * 订单号
     */
    private String number;

    /**
     * 用户Id
     */
    private int userId;
}
