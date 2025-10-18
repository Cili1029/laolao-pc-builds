package com.laolao.service;

import com.laolao.common.result.Result;
import com.laolao.pojo.entity.OrderDetail;

import java.util.List;

public interface OrderService {
    Result<Integer> createOrder();

    Result<List<OrderDetail>> grtOrderProduct(int id);
}
