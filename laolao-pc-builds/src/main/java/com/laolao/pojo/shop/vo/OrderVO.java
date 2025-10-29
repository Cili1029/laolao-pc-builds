package com.laolao.pojo.shop.vo;

import com.laolao.pojo.shop.entity.OrderDetail;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderVO {
    /**
     * 商品原金额
     */
    private BigDecimal originalAmount;

    /**
     * 商品优惠金额
     */
    private BigDecimal discountAmount;

    /**
     * 选中的地址id
     */
    private int addressId;

    /**
     * 商品信息
     */
    List<OrderDetail> orderDetails;
}
