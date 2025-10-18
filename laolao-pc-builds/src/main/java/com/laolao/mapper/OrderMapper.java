package com.laolao.mapper;

import com.laolao.pojo.entity.Order;
import com.laolao.pojo.entity.OrderDetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface OrderMapper {
    @Insert("insert into orders(number, user_id, amount, consignee, phone, address) value (#{number}, #{userId}, #{amount}, #{consignee}, #{phone}, #{address})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Order order);

    void insertToDetail(ArrayList<OrderDetail> orderDetails);

    @Select("select * from order_detail where order_id = #{id}")
    List<OrderDetail> select(int id);
}
