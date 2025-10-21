package com.laolao.mapper.shop;

import com.laolao.pojo.shop.entity.Order;
import com.laolao.pojo.shop.entity.OrderDetail;
import com.laolao.pojo.shop.vo.OrdersVO;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Mapper
public interface OrderMapper {
    @Insert("insert into shop_order(number, user_id, original_amount, consignee, phone, address) value (#{number}, #{userId}, #{originalAmount}, #{consignee}, #{phone}, #{address})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Order order);

    void insertToDetail(ArrayList<OrderDetail> orderDetails);

    // 获取的id是订单号
    @Select("select o.id, name, variant_name, image, order_id, quantity, price from shop_order_detail od join shop_order o on o.id = od.order_id where o.number = #{number}")
    List<OrderDetail> selectDetail(String number);

    void update(Order order);

    @Select("select * from shop_order where id = #{id}")
    Order selectAmount(int id);

    @Select("select * from shop_order where status = 1 and created_at < #{expire}")
    List<Order> getExpireOrders(int i, LocalDateTime expire);

    List<OrdersVO> selectOrders(int userId);

    @Select("select * from shop_order where user_id = ${userId} and number = #{number}")
    Order selectOrder(int userId, String number);
}
