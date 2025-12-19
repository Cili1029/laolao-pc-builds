package com.laolao.pojo.dashboard.vo;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ShopDashboardSummaryVO {
    /**
     * 组件数
     */
    private int componentCount;

    /**
     * 商品数
     */
    private int bundleCount;

    /**
     * 销售金额
     */
    private BigDecimal monthSalesAmount;

    /**
     * 订单数
     */
    private int orderCount;

    /**
     * 完成订单数
     */
    private int completedOrderCount;

    /**
     * 每月销售额
     */
    private List<MonthOrderAmountVO> monthAmount;

    /**
     * 销售最多的六个商品
     */
    private List<MonthProductVO> monthProducts;
}

