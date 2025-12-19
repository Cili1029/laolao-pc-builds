package com.laolao.pojo.dashboard.vo;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CompleteOrderDataVO {
    private int completedOrderCount;
    private BigDecimal monthSalesAmount;
}
