package com.laolao.service.admin.shop.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.laolao.common.constant.NotificationConstant;
import com.laolao.common.constant.OrderConstant;
import com.laolao.common.result.Result;
import com.laolao.converter.MapStruct;
import com.laolao.mapper.admin.shop.AdminOrderMapper;
import com.laolao.pojo.shop.dto.AdminOrderRejectDTO;
import com.laolao.pojo.shop.dto.AdminOrderShipDTO;
import com.laolao.pojo.shop.entity.Order;
import com.laolao.pojo.shop.entity.OrderDetail;
import com.laolao.pojo.shop.vo.AdminOrderDetailVO;
import com.laolao.pojo.shop.vo.AdminOrderVO;
import com.laolao.pojo.user.Listener.OrderNotification;
import com.laolao.service.admin.shop.AdminOrderService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminOrderServiceImpl implements AdminOrderService {
    @Resource
    private AdminOrderMapper adminOrderMapper;
    @Resource
    private MapStruct mapStruct;
    @Resource
    private ApplicationEventPublisher eventPublisher;

    @Override
    public Result<PageInfo<AdminOrderVO>> getCoupon(Integer status, String searchContent, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = adminOrderMapper.select(status, searchContent);
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
        adminOrderRejectDTO.setCancelTime(LocalDateTime.now());
        adminOrderMapper.rejectOrder(adminOrderRejectDTO);
        return Result.success("已拒单！");
    }

    @Override
    @Transactional
    public Result<String> ship(AdminOrderShipDTO adminOrderShipDTO) {
        adminOrderShipDTO.setStatus(OrderConstant.SHIPPED);
        adminOrderShipDTO.setShipTime(LocalDateTime.now());
        adminOrderMapper.ship(adminOrderShipDTO);

        // 发布异步通知
        OrderNotification orderNotification = OrderNotification.builder()
                .senderId(0)
                .type(NotificationConstant.SYS)
                .orderStatus(OrderConstant.SHIPPED)
                .orderId(adminOrderShipDTO.getId())
                .build();
        eventPublisher.publishEvent(orderNotification);

        return Result.success("已发货！");
    }

    @Override
    public Result<List<AdminOrderDetailVO>> getProduct(Integer orderId) {
        List<OrderDetail> orderDetailList = adminOrderMapper.selectOrderDetails(orderId);
        List<AdminOrderDetailVO> adminOrderDetailVOList = new ArrayList<>();
        for (OrderDetail orderDetail : orderDetailList) {
            AdminOrderDetailVO adminOrderDetailVO = mapStruct.orderDetailToorderDetailVO(orderDetail);
            adminOrderDetailVOList.add(adminOrderDetailVO);
        }
        return Result.success(adminOrderDetailVOList);
    }
}
