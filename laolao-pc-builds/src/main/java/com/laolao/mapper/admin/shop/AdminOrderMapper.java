package com.laolao.mapper.admin.shop;

import com.laolao.pojo.shop.dto.AdminOrderRejectDTO;
import com.laolao.pojo.shop.dto.AdminOrderShipDTO;
import com.laolao.pojo.shop.entity.Order;
import com.laolao.pojo.shop.entity.OrderDetail;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminOrderMapper {
    List<Order> select(Integer status, String searchContent);

    @Update("update shop_order set status = 6, cancel_time = #{cancelTime}, rejection_reason = #{rejectionReason} where id = #{id}")
    void rejectOrder(AdminOrderRejectDTO adminOrderRejectDTO);

    @Update("update shop_order set status = #{status}, tracking_no = #{trackingNo}, ship_time = #{shipTime} where id = #{id}")
    void ship(AdminOrderShipDTO adminOrderShipDTO);

    @Select("select id, name, variant_name, quantity, price from shop_order_detail where order_id = #{orderId}")
    List<OrderDetail> selectOrderDetails(Integer orderId);
}
