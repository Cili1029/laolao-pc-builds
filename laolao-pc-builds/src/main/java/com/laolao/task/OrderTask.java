package com.laolao.task;

import com.laolao.mapper.user.shop.OrderMapper;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrderTask {
    @Resource
    private OrderMapper orderMapper;

    // 模拟快递到达
    @Scheduled(cron = "0 0/5 * * * ?")
    public void arrivalOrders() {
        LocalDateTime expire = LocalDateTime.now().minusMinutes(5);
        orderMapper.arrivalOrder(expire, LocalDateTime.now());
    }
}
