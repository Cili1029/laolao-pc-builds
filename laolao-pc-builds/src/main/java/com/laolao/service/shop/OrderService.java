package com.laolao.service.shop;

import com.laolao.common.result.Result;
import com.laolao.pojo.shop.dto.CancelDTO;
import com.laolao.pojo.shop.dto.ChangeOrderAddressDTO;
import com.laolao.pojo.shop.dto.PayDTO;
import com.laolao.pojo.shop.vo.OrderDetailVO;
import com.laolao.pojo.shop.vo.OrdersVO;
import com.laolao.pojo.shop.vo.OrderVO;

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
