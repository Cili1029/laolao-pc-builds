package com.laolao.controller.user.shop;

import com.laolao.pojo.shop.dto.PayDTO;
import com.laolao.service.user.shop.PayService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/shop/pay")
public class PayController {
    @Resource
    private PayService payService;

    /**
     * 付款
     *
     * @param payDTO 订单号以及付款类型
     * @return 结果信息
     */
    @PatchMapping
    public String pay(@RequestBody PayDTO payDTO) {
        return payService.pay(payDTO);
    }

    /**
     * 支付宝同步回调（用户支付完成后跳转）
     * 同步回调不可作为支付成功的依据，仅用于页面跳转
     */
    @GetMapping("/return")
    public ResponseEntity alipayReturn(HttpServletRequest request) {
        return payService.alipayReturn(request);
    }

    /**
     * 支付宝异步回调（支付宝主动通知）
     * 异步回调是支付成功的唯一可靠依据，必须处理
     */
    @PostMapping("/notify")
    public String alipayNotify(HttpServletRequest request) {
        return payService.alipayNotify(request);
    }
}
