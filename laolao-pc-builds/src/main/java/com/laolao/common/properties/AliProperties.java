package com.laolao.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "aliyun")
public class AliProperties {
    private AccessKey accessKey;

    private Oss oss;

    private Pay pay;

    @Getter
    @Setter
    public static class AccessKey {
        private String accessKeyId;
        private String accessKeySecret;
    }

    @Getter
    @Setter
    public static class Oss {
        private String endpoint;
        private String bucketName;
    }

    @Getter
    @Setter
    public static class Pay {
        private String appId;
        private String privateKey;
        private String charset;
        private String signType;
        private String alipayPublicKey;
        private String gateway;
        private String notifyUrl;
        private String returnUrl;
    }
}
