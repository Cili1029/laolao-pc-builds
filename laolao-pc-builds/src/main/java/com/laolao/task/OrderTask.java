package com.laolao.task;

import com.laolao.common.constant.OrderConstant;
import com.laolao.mapper.user.shop.OrderMapper;
import com.laolao.mapper.user.shop.ShopCouponMapper;
import com.laolao.pojo.shop.entity.Order;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class OrderTask {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private ShopCouponMapper shopCouponMapper;

    @Scheduled(cron = "0 0/5 * * * ?")
    public void cleanupExpiredOrders() {
        LocalDateTime expire = LocalDateTime.now().minusMinutes(15);
        // 查找过期订单
        List<Order> orders =  orderMapper.getExpireOrders(expire);
        if (!orders.isEmpty()) {
            for (Order order : orders) {
                // 释放优惠券
                shopCouponMapper.cancelUseCoupon(order.getNumber());

                order.setDiscountAmount(BigDecimal.valueOf(0));
                order.setUserCouponId(null);
                order.setStatus(OrderConstant.CANCELLED);
                order.setCancelReason("订单超时自动取消");
                order.setCancelTime(LocalDateTime.now());
                orderMapper.updateExpire(order);
            }
        }
    }

    // 模拟快递到达
    @Scheduled(cron = "0 0/5 * * * ?")
    public void arrivalOrders() {
        LocalDateTime expire = LocalDateTime.now().minusMinutes(5);
        orderMapper.arrivalOrder(expire, LocalDateTime.now());
    }
}
