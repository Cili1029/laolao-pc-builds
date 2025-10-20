package com.laolao.service;

import com.laolao.common.result.Result;
import com.laolao.pojo.dto.CancelDTO;
import com.laolao.pojo.dto.ChangeOrderAddressDTO;
import com.laolao.pojo.dto.PayDTO;
import com.laolao.pojo.vo.OrderDetailVO;
import com.laolao.pojo.vo.OrdersVO;
import com.laolao.pojo.vo.OrderVO;

import java.util.List;

public interface OrderService {
    Result<String> createOrder();

    Result<OrderVO> getOrderProduct(String number);

    Result<String> changeAddress(ChangeOrderAddressDTO changeOrderAddressDTO);

    Result<List<OrdersVO>> getOrders();

    Result<OrderDetailVO> getOrderDetail(String number);

    Result<Integer> getStatus(String number);

    Result<String> cancelOrder(CancelDTO cancelDTO);

    Result<String> pay(PayDTO payDTO);
}
