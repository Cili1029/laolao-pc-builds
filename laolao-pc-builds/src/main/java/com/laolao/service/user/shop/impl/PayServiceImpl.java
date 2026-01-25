package com.laolao.service.user.shop.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.laolao.common.constant.OrderConstant;
import com.laolao.common.properties.AliProperties;
import com.laolao.common.result.Result;
import com.laolao.common.result.WsMessage;
import com.laolao.common.utils.AliPayUtil;
import com.laolao.common.websocket.NotificationHandler;
import com.laolao.mapper.user.shop.OrderMapper;
import com.laolao.pojo.shop.dto.PayDTO;
import com.laolao.pojo.shop.entity.Order;
import com.laolao.service.user.shop.PayService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class PayServiceImpl implements PayService {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private NotificationHandler notificationHandler;
    @Resource
    private AliPayUtil aliPayUtil;
    @Resource
    private AliProperties aliProperties;

    @Override
    public Result<String> pay(PayDTO payDTO) {
        if (payDTO.getPayType() == 1) {
            // 数据库查钱
            BigDecimal totalAmount = orderMapper.selectAmountByNumber(payDTO.getNumber());
            // 支付宝付款
            return alipay(payDTO.getNumber(), totalAmount.toString(), payDTO.getSubject());
        }

        // 假付款，付款流程省去。。。
        changeOrderStatus(payDTO.getNumber());
        // websocket通知管理员
        notificationHandler.sendToAllAdmins(WsMessage.of("new_order", "订单号：" + payDTO.getNumber()));
        return Result.success("付款成功");
    }

    /**
     * 发起电脑网站支付（沙箱测试）
     *
     * @param outTradeNo  商户订单号（自己生成，唯一）
     * @param totalAmount 订单金额（沙箱测试建议小额，如0.01）
     * @param subject     订单标题
     * @return 支付页面HTML（前端直接渲染即可跳转支付宝沙箱支付）
     */
    public Result<String> alipay(String outTradeNo, String totalAmount, String subject) {
        try {
            AlipayClient alipayClient = aliPayUtil.createAlipayClient();
            // 创建支付请求
            AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
            // 设置异步回调网址
            request.setReturnUrl(aliProperties.getPay().getReturnUrl());
            request.setNotifyUrl(aliProperties.getPay().getNotifyUrl());
            // 组装支付参数
            String bizContent = "{" +
                    "\"out_trade_no\":\"" + outTradeNo + "\"," +
                    "\"total_amount\":\"" + totalAmount + "\"," +
                    "\"subject\":\"" + subject + "\"," +
                    "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"" +
                    "}";
            request.setBizContent(bizContent);

            return Result.success(alipayClient.pageExecute(request).getBody(), "");
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return Result.success("同步回调处理失败：" + e.getErrMsg());
        }
    }

    public ResponseEntity<Void> alipayReturn(HttpServletRequest request) {
        try {
            // 获取回调参数
            Map<String, String> params = new HashMap<>();
            request.getParameterMap().forEach((key, values) -> params.put(key, values[0]));

            // 验证签名（关键）
            boolean signVerified = AlipaySignature.rsaCheckV1(
                    params,
                    aliProperties.getPay().getAlipayPublicKey(),
                    aliProperties.getPay().getCharset(),
                    aliProperties.getPay().getSignType()
            );

            if (signVerified) {
                // 签名验证通过，获取订单信息
                return ResponseEntity.status(302).header("Location", "http://localhost:5173/pay-success").build();
            } else {
                return ResponseEntity.status(302).header("Location", "http://localhost:5173/error").build();
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return ResponseEntity.status(302).header("Location", "http://localhost:5173/error").build();
        }
    }

    public String alipayNotify(HttpServletRequest request) {
        try {
            // 获取回调参数
            Map<String, String> params = new HashMap<>();
            request.getParameterMap().forEach((key, values) -> params.put(key, values[0]));

            // 验证签名
            boolean signVerified = AlipaySignature.rsaCheckV1(
                    params,
                    aliProperties.getPay().getAlipayPublicKey(),
                    aliProperties.getPay().getCharset(),
                    aliProperties.getPay().getSignType()
            );

            if (signVerified) {
                // 签名验证通过，处理业务逻辑
                String outTradeNo = params.get("out_trade_no"); // 商户订单号
//                String tradeNo = params.get("trade_no"); // 支付宝交易号 现在没啥用
                String tradeStatus = params.get("trade_status"); // 交易状态

                // 只有交易状态为TRADE_SUCCESS或TRADE_FINISHED才代表支付成功
                if ("TRADE_SUCCESS".equals(tradeStatus) || "TRADE_FINISHED".equals(tradeStatus)) {
                    changeOrderStatus(outTradeNo);
                    // websocket通知管理员
                    notificationHandler.sendToAllAdmins(WsMessage.of("new_order", "订单号：" + outTradeNo));
                    // 必须返回"success"给支付宝，否则支付宝会重复通知
                    return "success";
                }
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        // 验证失败或处理异常，返回"fail"
        return "fail";
    }

    private void changeOrderStatus(String number) {
        Order order = new Order();
        order.setNumber(number);
        order.setStatus(OrderConstant.PENDING_SHIPMENT);
        order.setCheckoutTime(LocalDateTime.now());
        orderMapper.update(order);
    }
}
