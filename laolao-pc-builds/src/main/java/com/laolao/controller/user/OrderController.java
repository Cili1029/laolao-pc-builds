package com.laolao.controller.user;

import com.laolao.common.result.Result;
import com.laolao.pojo.dto.ChangeOrderAddressDTO;
import com.laolao.pojo.vo.OrdersVO;
import com.laolao.pojo.vo.OrderVO;
import com.laolao.service.OrderService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单相关
 */
@RestController
@RequestMapping("/user/order")
public class OrderController {
    @Resource
    private OrderService orderService;

    /**
     * 创建订单
     *
     * @return 结果信息
     */
    @PostMapping("/create")
    public Result<Integer> createOrder() {
        return orderService.createOrder();
    }

    /**
     * 获取订单详细
     *
     * @param id 订单Id
     * @return 订单详细数据
     */
    @GetMapping("/list")
    public Result<OrderVO> getOrderProduct(int id) {
        return orderService.getOrderProduct(id);
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
}
