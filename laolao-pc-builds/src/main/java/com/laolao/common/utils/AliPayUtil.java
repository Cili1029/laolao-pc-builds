package com.laolao.common.utils;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.laolao.common.properties.AliProperties;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class AliPayUtil {
    @Resource
    private AliProperties aliProperties;

    public AlipayClient createAlipayClient() {
        return new DefaultAlipayClient(
                aliProperties.getPay().getGateway(),
                aliProperties.getPay().getAppId(),
                aliProperties.getPay().getPrivateKey(),
                "json",
                aliProperties.getPay().getCharset(),
                aliProperties.getPay().getAlipayPublicKey(),
                aliProperties.getPay().getSignType()
        );
    }
}
