package com.laolao.service.user.shop;

import com.laolao.common.result.Result;
import com.laolao.pojo.shop.dto.PayDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;


public interface PayService {
    Result<String> pay(PayDTO payDTO);
    ResponseEntity<Void> alipayReturn(HttpServletRequest request);
    String alipayNotify(HttpServletRequest request);
}
