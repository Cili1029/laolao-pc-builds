package com.laolao.service.user.shop.impl;

import com.laolao.common.constant.CommonConstant;
import com.laolao.common.constant.MessageConstant;
import com.laolao.common.context.UserContext;
import com.laolao.common.exception.UnknownError;
import com.laolao.common.result.Result;
import com.laolao.common.result.WsMessage;
import com.laolao.common.websocket.NotificationHandler;
import com.laolao.converter.MapStruct;
import com.laolao.mapper.user.shop.*;
import com.laolao.pojo.shop.dto.*;
import com.laolao.pojo.shop.entity.*;
import com.laolao.pojo.shop.vo.*;
import com.laolao.service.user.shop.CartService;
import com.laolao.service.user.shop.OrderService;
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
    @Resource
    private NotificationHandler notificationHandler;

    @Transactional
    @Override
    public Result<String> createOrderFromCart() {
        Result<List<CartProductVO>> cartProductList = cartService.getCartProductList();
        List<CartProductVO> data = cartProductList.getData();
        // 减库存
        // 组件的id
        int userId = UserContext.getCurrentId();
        List<CartItem> cartItemList = cartMapper.getList(userId);
        List<IdAndQuantityVO> components = cartItemList.stream()
                .filter(cartItem -> cartItem.getProductType() == CommonConstant.Product.COMPONENT)
                .map(cartItem -> new IdAndQuantityVO(
                        cartItem.getProductId(),
                        cartItem.getQuantity()
                ))
                .toList();
        // 整机的id
        List<IdAndQuantityVO> bundles = cartItemList.stream()
                .filter(cartItem -> cartItem.getProductType() == CommonConstant.Product.BUNDLE)
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
        Order order = new Order();
        // 生成订单号
        String number = "ORD" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + (int) ((Math.random() * 900) + 100);
        // 下单用户
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
    public Result<String> createOrderDirectly(BuyProductDTO buyProductDTO) {
        int userId = UserContext.getCurrentId();
        IdAndQuantityVO idAndQuantityVO = new IdAndQuantityVO();
        idAndQuantityVO.setProductId(buyProductDTO.getProductId());
        idAndQuantityVO.setQuantity(buyProductDTO.getQuantity());
        // 获取产品信息
        // 减库存
        OrderProductVO orderProductVO;
        if (buyProductDTO.getProductType() == 1) {
            orderProductVO = componentMapper.selectOrderProduct(buyProductDTO.getProductId());
            orderProductVO.setQuantity(buyProductDTO.getQuantity());
            componentMapper.updateStock(List.of(idAndQuantityVO));
        } else {
            orderProductVO = bundleMapper.selectOrderBundle(buyProductDTO.getProductId());
            orderProductVO.setQuantity(buyProductDTO.getQuantity());
            bundleMapper.updateStock(List.of(idAndQuantityVO));
        }

        // 订单表
        Order order = new Order();
        // 生成订单号
        String number = "ORD" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + (int) ((Math.random() * 900) + 100);
        // 下单用户
        order.setNumber(number);
        order.setUserId(userId);

        // 计算总价
        BigDecimal originalAmount = orderProductVO.getPrice().multiply(BigDecimal.valueOf(orderProductVO.getQuantity()));
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
        OrderDetail orderDetail = mapStruct.orderProductVOToOrderDetail(orderProductVO);
        orderDetail.setOrderId(id);
        orderDetails.add(orderDetail);
        orderMapper.insertToDetail(orderDetails);

        return Result.success(number, "创建订单成功");
    }

    @Override
    public Result<String> receive(String number) {
        orderMapper.receiveOrder(number, LocalDateTime.now());
        return Result.success("确认收货成功");
    }

    @Override
    public Result<Integer> getStatus(String number) {
        int userId = UserContext.getCurrentId();
        Order order = orderMapper.selectOrder(userId, number);
        if (order.getStatus() != CommonConstant.Order.PENDING_PAYMENT) {
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
        if (order.getUserCouponId() != null) {
            orderVO.setUserCouponId(order.getUserCouponId());
        }
        // 获取已选择的地址
        orderVO.setAddressId(order.getAddressId());
        return Result.success(orderVO);
    }

    @Override
    public Result<String> changeAddress(ChangeOrderAddressDTO changeOrderAddressDTO) {
        // 查找地址
        int userId = UserContext.getCurrentId();
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
        int userId = UserContext.getCurrentId();
        // 订单获取基础数据 并且只获取一张图片
        List<OrdersVO> orderList = orderMapper.selectOrders(userId);
        return Result.success(orderList);
    }

    @Override
    public Result<OrderDetailVO> getOrderDetail(String number) {
        // 获取的订单信息
        int userId = UserContext.getCurrentId();
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
        // 释放优惠券
        shopCouponMapper.cancelUseCoupon(cancelDTO.getNumber());

        int userId = UserContext.getCurrentId();
        Order order = new Order();
        order.setUserId(userId);
        order.setNumber(cancelDTO.getNumber());
        order.setStatus(CommonConstant.Order.CANCELLED);
        order.setCancelReason(cancelDTO.getCancelReason());
        order.setCancelTime(LocalDateTime.now());
        order.setDiscountAmount(BigDecimal.valueOf(0));
        order.setUserCouponId(null);
        orderMapper.updateExpire(order);
        return Result.success("取消成功");
    }

    @Override
    public Result<String> pay(PayDTO payDTO) {
        // 付款流程省去。。。
        int userId = UserContext.getCurrentId();
        Order order = new Order();
        order.setUserId(userId);
        order.setNumber(payDTO.getNumber());
        order.setStatus(CommonConstant.Order.PENDING_SHIPMENT);
        order.setCheckoutTime(LocalDateTime.now());
        orderMapper.update(order);
        // websocket通知管理员
        notificationHandler.sendToAllAdmins(WsMessage.of("new_order", "订单号：" + payDTO.getNumber()));
        return Result.success("付款成功");
    }

    @Override
    public Result<String> useCoupon(CouponDTO couponDTO) {
        int userId = UserContext.getCurrentId();
        couponDTO.setUserId(userId);
        couponDTO.setStatus(CommonConstant.CommonStatus.USED);
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
        int userId = UserContext.getCurrentId();
        couponDTO.setUserId(userId);
        couponDTO.setStatus(CommonConstant.CommonStatus.NOT_USED);
        couponDTO.setUsedAt(null);
        orderMapper.updateCoupon(null, BigDecimal.valueOf(0), couponDTO.getNumber());
        couponDTO.setNumber(null);
        shopCouponMapper.UpdateCoupon(couponDTO);
        return Result.success("取消使用！");
    }
}
