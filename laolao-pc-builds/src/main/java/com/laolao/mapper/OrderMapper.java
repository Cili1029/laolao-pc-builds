package com.laolao.mapper;

import com.laolao.pojo.entity.Order;
import com.laolao.pojo.entity.OrderDetail;
import com.laolao.pojo.vo.OrdersVO;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Mapper
public interface OrderMapper {
    @Insert("insert into orders(number, user_id, original_amount, consignee, phone, address) value (#{number}, #{userId}, #{originalAmount}, #{consignee}, #{phone}, #{address})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Order order);

    void insertToDetail(ArrayList<OrderDetail> orderDetails);

    @Select("select * from order_detail where order_id = #{id}")
    List<OrderDetail> selectDetail(int id);

    void update(Order order);

    @Select("select * from orders where id = #{id}")
    Order selectAmount(int id);

    @Select("select * from orders where status = 1 and created_at < #{expire}")
    List<Order> getExpireOrders(int i, LocalDateTime expire);

    List<OrdersVO> selectOrders(int userId);
}
