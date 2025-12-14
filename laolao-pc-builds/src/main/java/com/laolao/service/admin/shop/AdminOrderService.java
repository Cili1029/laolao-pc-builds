package com.laolao.service.admin.shop;

import com.github.pagehelper.PageInfo;
import com.laolao.common.result.Result;
import com.laolao.pojo.shop.dto.AdminOrderRejectDTO;
import com.laolao.pojo.shop.dto.AdminOrderShipDTO;
import com.laolao.pojo.shop.entity.OrderDetail;
import com.laolao.pojo.shop.vo.AdminOrderDetailVO;
import com.laolao.pojo.shop.vo.AdminOrderVO;

import java.util.List;


public interface AdminOrderService {
    Result<PageInfo<AdminOrderVO>> getCoupon(Integer status, Integer pageNum, Integer pageSize);

    Result<String> rejectOrder(AdminOrderRejectDTO adminOrderRejectDTO);

    Result<String> ship(AdminOrderShipDTO adminOrderShipDTO);

    Result<List<AdminOrderDetailVO>> getProduct(Integer orderId);
}
