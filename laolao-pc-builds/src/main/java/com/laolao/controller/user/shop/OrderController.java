package com.laolao.controller.user.shop;

import com.laolao.common.result.Result;
import com.laolao.pojo.shop.dto.*;
import com.laolao.pojo.shop.vo.OrderDetailVO;
import com.laolao.pojo.shop.vo.OrdersVO;
import com.laolao.pojo.shop.vo.OrderVO;
import com.laolao.service.user.shop.OrderService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户端-订单相关
 */
@RestController
@RequestMapping("/user/shop/order")
public class OrderController {
    @Resource
    private OrderService orderService;

    /**
     * 创建订单（购物车）
     *
     * @return 结果信息
     */
    @PostMapping("/create")
    public Result<String> createOrderFromCart() {
        return orderService.createOrderFromCart();
    }

    /**
     * 创建订单（直接购买）
     *
     * @return 结果信息
     */
    @PostMapping("/directly")
    public Result<String> createOrderDirectly(@RequestBody BuyProductDTO buyProductDTO) {
        return orderService.createOrderDirectly(buyProductDTO);
    }

    /**
     * 获取订单状态
     * 为1才能进入结算页面
     *
     * @param number 订单号
     * @return 结果信息
     */
    @GetMapping("/status")
    public Result<Integer> getStatus(String number) {
        return orderService.getStatus(number);
    }

    /**
     * 获取订单详细
     *
     * @param number 订单号
     * @return 订单详细数据
     */
    @GetMapping("/list")
    public Result<OrderVO> getOrderProduct(String number) {
        return orderService.getOrderProduct(number);
    }

    /**
     * 修改订单地址
     *
     * @return 结果信息
     */
    @PatchMapping("/address")
    public Result<String> changeAddress(@RequestBody ChangeOrderAddressDTO changeOrderAddressDTO) {
        return orderService.changeAddress(changeOrderAddressDTO);
    }

    /**
     * 获取全部订单
     *
     * @return 订单数据
     */
    @GetMapping("/my-orders")
    public Result<List<OrdersVO>> getOrders() {
        return orderService.getOrders();
    }

    /**
     * 获取某个的订单详情
     *
     * @param number 订单号
     * @return 订单详情数据
     */
    @GetMapping("/detail")
    public Result<OrderDetailVO> getOrderDetail(String number) {
        return orderService.getOrderDetail(number);
    }

    /**
     * 取消订单
     *
     * @param cancelDTO 订单号和原因
     * @return 结果信息
     */
    @PatchMapping("/cancel")
    public Result<String> cancelOrder(@RequestBody CancelDTO cancelDTO) {
        return orderService.cancelOrder(cancelDTO);
    }

    /**
     * 付款
     * @param payDTO 订单号
     * @return 结果信息
     */
    @PatchMapping("/pay")
    public Result<String> pay(@RequestBody PayDTO payDTO) {
        return orderService.pay(payDTO);
    }

    /**
     * 选择优惠券
     *
     * @param couponDTO 基本数据
     * @return 结果信息
     */
    @PatchMapping("/coupon")
    public Result<String> useCoupon(@RequestBody CouponDTO couponDTO) {
        return orderService.useCoupon(couponDTO);
    }

    /**
     * 取消使用优惠券
     *
     * @param couponDTO 基本数据
     * @return 结果信息
     */
    @PatchMapping("/coupon/cancel")
    public Result<String> cancelCoupon(@RequestBody CouponDTO couponDTO) {
        return orderService.cancelCoupon(couponDTO);
    }


    @PatchMapping("/receive")
    public Result<String> receive(@RequestParam String number) {
        return orderService.receive(number);
    }


}
