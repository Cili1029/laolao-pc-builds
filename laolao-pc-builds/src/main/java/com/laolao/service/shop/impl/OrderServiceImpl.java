package com.laolao.service.shop.impl;

import com.laolao.common.constant.MessageConstant;
import com.laolao.common.context.BaseContext;
import com.laolao.common.exception.UnknownError;
import com.laolao.common.result.Result;
import com.laolao.converter.MapStruct;
import com.laolao.mapper.shop.*;
import com.laolao.pojo.shop.dto.CancelDTO;
import com.laolao.pojo.shop.dto.ChangeOrderAddressDTO;
import com.laolao.pojo.shop.dto.PayDTO;
import com.laolao.pojo.shop.dto.CouponDTO;
import com.laolao.pojo.shop.entity.*;
import com.laolao.pojo.shop.vo.*;
import com.laolao.service.shop.CartService;
import com.laolao.service.shop.OrderService;
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
    @Resource
    private ComponentMapper componentMapper;
    @Resource
    private BundleMapper bundleMapper;
    @Resource
    private ShopCouponMapper shopCouponMapper;

    @Transactional
    @Override
    public Result<String> createOrder() {
        Result<List<CartProductVO>> cartProductList = cartService.getCartProductList();
        List<CartProductVO> data = cartProductList.getData();
        // 减库存
        // 组件的id
        int userId = BaseContext.getCurrentId();
        List<CartItem> cartItemList = cartMapper.getList(userId);
        List<IdAndQuantityVO> components = cartItemList.stream()
                .filter(cartItem -> cartItem.getProductType() == 1)
                .map(cartItem -> new IdAndQuantityVO(
                        cartItem.getProductId(),
                        cartItem.getQuantity()
                ))
                .toList();
        // 整机的id
        List<IdAndQuantityVO> bundles = cartItemList.stream()
                .filter(cartItem -> cartItem.getProductType() == 2)
                .map(cartItem -> new IdAndQuantityVO(
                        cartItem.getProductId(),
                        cartItem.getQuantity()
                ))
                .toList();
        if (!components.isEmpty()) {
            componentMapper.updateStock(components);
        }
        if (!bundles.isEmpty()) {
            bundleMapper.updateStock(bundles);
        }

        // 订单表
        // 生成订单号
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String random = String.valueOf((int) ((Math.random() * 900) + 100));
        String number = "ORD" + date + random;
        // 下单用户
        Order order = new Order();
        order.setNumber(number);
        order.setUserId(userId);

        // 计算总价
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
            order.setAddressId(address.getId());
        }
        orderMapper.insert(order);
        int id = order.getId();

        // 商品详细表
        ArrayList<OrderDetail> orderDetails = new ArrayList<>();
        for (CartProductVO cartProductVO : data) {
            OrderDetail orderDetail = mapStruct.cartProductVOToOrderDetail(cartProductVO);
            orderDetail.setOrderId(id);
            orderDetails.add(orderDetail);
        }

        orderMapper.insertToDetail(orderDetails);

        // 清空购物车
        cartMapper.clear(userId);

        return Result.success(number, "创建订单成功");
    }

    @Override
    public Result<Integer> getStatus(String number) {
        int userId = BaseContext.getCurrentId();
        Order order = orderMapper.selectOrder(userId, number);
        if (order.getStatus() != 1) {
            throw new UnknownError(MessageConstant.UNKNOWN_ERROR);
        }

        return Result.success(order.getStatus());
    }

    @Override
    public Result<OrderVO> getOrderProduct(String number) {
        List<OrderDetail> orderDetailList = orderMapper.selectDetail(number);
        OrderVO orderVO = new OrderVO();
        orderVO.setOrderDetails(orderDetailList);
        // 获取金额
        Order order = orderMapper.selectOrderById(orderDetailList.get(0).getId());
        orderVO.setOriginalAmount(order.getOriginalAmount());
        orderVO.setDiscountAmount(order.getDiscountAmount());
        orderVO.setUserCouponId(order.getUserCouponId());
        // 获取已选择的地址
        orderVO.setAddressId(order.getAddressId());
        return Result.success(orderVO);
    }

    @Override
    public Result<String> changeAddress(ChangeOrderAddressDTO changeOrderAddressDTO) {
        // 查找地址
        int userId = BaseContext.getCurrentId();
        Address address = addressMapper.get(userId, changeOrderAddressDTO.getAddressId());
        // 更新地址
        Order order = new Order();
        order.setNumber(changeOrderAddressDTO.getNumber());
        order.setUserId(userId);
        order.setConsignee(address.getConsignee());
        order.setPhone(address.getPhone());
        order.setAddressId(changeOrderAddressDTO.getAddressId());
        order.setAddress(address.getProvince() + address.getCity() + address.getDistrict() + address.getDetailAddress());
        orderMapper.update(order);
        return Result.success("成功！");
    }

    @Override
    public Result<List<OrdersVO>> getOrders() {
        int userId = BaseContext.getCurrentId();
        // 订单获取基础数据 并且只获取一张图片
        List<OrdersVO> orderList = orderMapper.selectOrders(userId);
        return Result.success(orderList);
    }

    @Override
    public Result<OrderDetailVO> getOrderDetail(String number) {
        // 获取的订单信息
        int userId = BaseContext.getCurrentId();
        Order order = orderMapper.selectOrder(userId, number);
        // 获取所包含的商品
        List<OrderDetail> orderDetailList = orderMapper.selectDetail(order.getNumber());
        List<OrderProductVO> orderProductVOList = new ArrayList<>();
        for (OrderDetail orderDetail : orderDetailList) {
            OrderProductVO orderProductVO = mapStruct.orderDeatilToOrderProductVO(orderDetail);
            orderProductVOList.add(orderProductVO);
        }

        OrderDetailVO orderDetailVO = mapStruct.orderToOrderDetailVO(order);
        orderDetailVO.setProducts(orderProductVOList);
        return Result.success(orderDetailVO);
    }

    @Override
    public Result<String> cancelOrder(CancelDTO cancelDTO) {
        int userId = BaseContext.getCurrentId();
        Order order = new Order();
        order.setUserId(userId);
        order.setNumber(cancelDTO.getNumber());
        order.setStatus(6);
        order.setCancelReason(cancelDTO.getCancelReason());
        order.setCancelTime(LocalDateTime.now());
        orderMapper.update(order);
        return Result.success("取消成功");
    }

    @Override
    public Result<String> pay(PayDTO payDTO) {
        // 付款流程省去。。。
        int userId = BaseContext.getCurrentId();
        Order order = new Order();
        order.setUserId(userId);
        order.setNumber(payDTO.getNumber());
        order.setStatus(2);
        order.setCheckoutTime(LocalDateTime.now());
        orderMapper.update(order);
        return Result.success("付款成功");
    }

    @Override
    public Result<String> useCoupon(CouponDTO couponDTO) {
        int userId = BaseContext.getCurrentId();
        couponDTO.setUserId(userId);
        couponDTO.setStatus(1);
        couponDTO.setUsedAt(LocalDateTime.now());
        // 查询优惠券信息折扣
        BigDecimal discountAmount = shopCouponMapper.selectShopCouponById(couponDTO.getId());
        // 更新优惠券信息
        // 取消所有此订单选中的优惠券
        shopCouponMapper.cancelUseCoupon(couponDTO.getNumber());
        // 使用此优惠券
        shopCouponMapper.UpdateCoupon(couponDTO);
        // 更新订单使用优惠券
        orderMapper.updateCoupon(couponDTO.getId(), discountAmount, couponDTO.getNumber());
        return Result.success("成功！");
    }

    @Override
    public Result<String> cancelCoupon(CouponDTO couponDTO) {
        int userId = BaseContext.getCurrentId();
        couponDTO.setUserId(userId);
        couponDTO.setStatus(0);
        couponDTO.setUsedAt(null);
        orderMapper.updateCoupon(null, BigDecimal.valueOf(0), couponDTO.getNumber());
        couponDTO.setNumber(null);
        shopCouponMapper.UpdateCoupon(couponDTO);
        return Result.success("取消使用！");
    }
}
