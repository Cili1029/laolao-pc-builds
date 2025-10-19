package com.laolao.service.impl;

import com.laolao.common.context.BaseContext;
import com.laolao.common.result.Result;
import com.laolao.converter.MapStruct;
import com.laolao.mapper.AddressMapper;
import com.laolao.mapper.CartMapper;
import com.laolao.mapper.OrderMapper;
import com.laolao.pojo.dto.ChangeOrderAddressDTO;
import com.laolao.pojo.entity.Address;
import com.laolao.pojo.entity.Order;
import com.laolao.pojo.entity.OrderDetail;
import com.laolao.pojo.vo.CartProductVO;
import com.laolao.pojo.vo.OrdersVO;
import com.laolao.pojo.vo.OrderVO;
import com.laolao.service.CartService;
import com.laolao.service.OrderService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private CartService cartService;
    @Resource
    private MapStruct mapStruct;
    @Resource
    private CartMapper cartMapper;
    @Resource
    private AddressMapper addressMapper;

    @Transactional
    @Override
    public Result<Integer> createOrder() {
        // 订单表
        // 生成订单号
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String random = String.valueOf((int) ((Math.random() * 900) + 100));
        String orderNumber = "ORD" + date + random;
        // 下单用户
        int userId = BaseContext.getCurrentId();

        Order order = new Order();
        order.setNumber(orderNumber);
        order.setUserId(userId);

        // 计算总价
        Result<List<CartProductVO>> cartProductList = cartService.getCartProductList();
        List<CartProductVO> data = cartProductList.getData();
        BigDecimal originalAmount = data.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setOriginalAmount(originalAmount);

        //设置收货人，有默认用默认，无默认不填
        Address address = addressMapper.getDefault(userId);
        if (address != null) {
            order.setConsignee(address.getConsignee());
            order.setPhone(address.getPhone());
            order.setAddress(address.getProvince() + address.getCity() + address.getDistrict() + address.getDetailAddress());
        }
        orderMapper.insert(order);
        int id = order.getId();

        // 商品详细表
        ArrayList<OrderDetail> orderDetails = new ArrayList<>();
        for (CartProductVO cartProductVO : data) {
            OrderDetail orderDetail = mapStruct.CartProductVOToOrderDetail(cartProductVO);
            orderDetail.setOrderId(id);
            orderDetails.add(orderDetail);
        }

        orderMapper.insertToDetail(orderDetails);

        // 清空购物车
        cartMapper.clear(userId);

        return Result.success(id, "创建订单成功");
    }

    @Override
    public Result<OrderVO> getOrderProduct(int id) {
        List<OrderDetail> orderDetailList = orderMapper.selectDetail(id);
        OrderVO orderVO = new OrderVO();
        orderVO.setOrderDetails(orderDetailList);
        // 获取金额
        Order order = orderMapper.selectAmount(id);
        orderVO.setOriginalAmount(order.getOriginalAmount());
        orderVO.setDiscountAmount(order.getDiscountAmount());
        return Result.success(orderVO);
    }

    @Override
    public Result<String> changeAddress(ChangeOrderAddressDTO changeOrderAddressDTO) {
        // 查找地址
        int userId = BaseContext.getCurrentId();
        Address address = addressMapper.get(userId, changeOrderAddressDTO.getAddressId());
        // 更新地址
        Order order = new Order();
        order.setId(changeOrderAddressDTO.getOrderId());
        order.setUserId(userId);
        order.setConsignee(address.getConsignee());
        order.setPhone(address.getPhone());
        order.setAddress(address.getProvince() + address.getCity() + address.getDistrict() + address.getDetailAddress());
        orderMapper.update(order);
        return Result.success("修改成功！");
    }
    @Override
    public Result<List<OrdersVO>> getOrders() {
        int userId = BaseContext.getCurrentId();
        // 订单获取基础数据 并且只获取一张图片
        List<OrdersVO> orderList = orderMapper.selectOrders(userId);
        return Result.success(orderList);
    }
}
