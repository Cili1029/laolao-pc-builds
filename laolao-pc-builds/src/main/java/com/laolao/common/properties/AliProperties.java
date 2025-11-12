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
}
