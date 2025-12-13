package com.laolao.mapper.admin.shop;

import com.laolao.pojo.shop.dto.AdminOrderRejectDTO;
import com.laolao.pojo.shop.entity.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminOrderMapper {
    List<Order> select(Integer status);

    @Update("update shop_order set status = 6, rejection_reason = #{rejectionReason} where id = #{id}")
    void rejectOrder(AdminOrderRejectDTO adminOrderRejectDTO);
}
