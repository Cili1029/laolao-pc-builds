package com.laolao.controller.user;

import com.laolao.common.result.Result;
import com.laolao.pojo.entity.OrderDetail;
import com.laolao.service.OrderService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/order")
public class OrderController {
    @Resource
    private OrderService orderService;

    @PostMapping("/create")
    public Result<Integer> createOrder() {
        return orderService.createOrder();
    }

    @GetMapping("/list")
    public Result<List<OrderDetail>> grtOrderProduct(int id) {
        return orderService.grtOrderProduct(id);
    }
}
