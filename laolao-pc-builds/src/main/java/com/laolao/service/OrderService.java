package com.laolao.service;

import com.laolao.common.result.Result;
import com.laolao.pojo.dto.ChangeOrderAddressDTO;
import com.laolao.pojo.vo.OrdersVO;
import com.laolao.pojo.vo.OrderVO;

import java.util.List;

public interface OrderService {
    Result<Integer> createOrder();

    Result<OrderVO> getOrderProduct(int id);

    Result<String> changeAddress(ChangeOrderAddressDTO changeOrderAddressDTO);

    Result<List<OrdersVO>> getOrders();
}
