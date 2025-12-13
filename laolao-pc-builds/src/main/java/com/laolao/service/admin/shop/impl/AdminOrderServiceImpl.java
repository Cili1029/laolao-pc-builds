package com.laolao.service.admin.shop.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.laolao.common.result.Result;
import com.laolao.converter.MapStruct;
import com.laolao.mapper.admin.shop.AdminOrderMapper;
import com.laolao.pojo.shop.dto.AdminOrderRejectDTO;
import com.laolao.pojo.shop.entity.Order;
import com.laolao.pojo.shop.vo.AdminOrderVO;
import com.laolao.service.admin.shop.AdminOrderService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminOrderServiceImpl implements AdminOrderService {

    @Resource
    private AdminOrderMapper adminOrderMapper;
    @Resource
    private MapStruct mapStruct;

    @Override
    public Result<PageInfo<AdminOrderVO>> getCoupon(Integer status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = adminOrderMapper.select(status);
        List<AdminOrderVO> adminOrderVOList = new ArrayList<>();
        for (Order order : orderList) {
            AdminOrderVO vo = mapStruct.orderToAdminOrderVO(order);
            adminOrderVOList.add(vo);
        }

        // 转换提取
        PageInfo<Order> orderPageInfo = new PageInfo<>(orderList);
        PageInfo<AdminOrderVO> resultPageInfo = new PageInfo<>();
        BeanUtils.copyProperties(orderPageInfo, resultPageInfo);

        // setVO
        resultPageInfo.setList(adminOrderVOList);
        return Result.success(resultPageInfo);
    }

    @Override
    public Result<String> rejectOrder(AdminOrderRejectDTO adminOrderRejectDTO) {
        adminOrderMapper.rejectOrder(adminOrderRejectDTO);
        return Result.success("已拒单！");
    }
}
