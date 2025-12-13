package com.laolao.service.admin.shop;

import com.github.pagehelper.PageInfo;
import com.laolao.common.result.Result;
import com.laolao.pojo.shop.dto.AdminOrderRejectDTO;
import com.laolao.pojo.shop.vo.AdminOrderVO;


public interface AdminOrderService {
    Result<PageInfo<AdminOrderVO>> getCoupon(Integer status, Integer pageNum, Integer pageSize);

    Result<String> rejectOrder(AdminOrderRejectDTO adminOrderRejectDTO);
}
