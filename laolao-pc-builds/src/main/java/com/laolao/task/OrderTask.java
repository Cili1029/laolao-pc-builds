package com.laolao.task;

import com.laolao.mapper.OrderMapper;
import com.laolao.pojo.entity.Order;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class OrderTask {
    @Resource
    private OrderMapper orderMapper;

    @Scheduled(cron = "0 0/5 * * * ?")
    public void cleanupExpiredOrders() {
        LocalDateTime expire = LocalDateTime.now().minusMinutes(15);
        // 查找过期订单
        List<Order> orders =  orderMapper.getExpireOrders(1, expire);
        if (!orders.isEmpty()) {
            for (Order order : orders) {
                order.setStatus(6);
                order.setCancelReason("订单超时");
                order.setCancelTime(LocalDateTime.now());
                orderMapper.update(order);
            }
        }
    }
}
