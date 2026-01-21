package com.laolao.consumer;

import com.laolao.common.constant.OrderConstant;
import com.laolao.common.constant.RocketMQConstant;
import com.laolao.mapper.user.shop.OrderMapper;
import com.laolao.mapper.user.shop.ShopCouponMapper;
import com.laolao.pojo.shop.entity.Order;
import jakarta.annotation.Resource;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RocketMQMessageListener(
        topic = RocketMQConstant.ORDER_TIMEOUT_TOPIC,
        consumerGroup = RocketMQConstant.ORDER_TIMEOUT_CONSUMER_GROUP
)
public class OrderConsumer implements RocketMQListener<Integer> {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private ShopCouponMapper shopCouponMapper;
    @Resource
    @Lazy
    private OrderConsumer self;

    @Override
    public void onMessage(Integer id) {
        // 查询订单状态
        Order order = orderMapper.selectOrderById(id);
        if (order != null && order.getStatus() == OrderConstant.PENDING_PAYMENT) {
            // 过期订单
            self.closeOrder(order);
        } else {
            System.out.println("订单" + id + "已支付或已处理，跳过");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void closeOrder(Order order) {
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
