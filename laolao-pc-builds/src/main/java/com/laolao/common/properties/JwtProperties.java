package com.laolao.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "laolao.jwt")
public class JwtProperties {
    /**
     * 用户生成jwt令牌相关配置
     */
    private String userSecretKey;
    private long userTtl;
    private String userTokenName;

    public String getUserSecretKey() {
        return userSecretKey;
    }

    public void setUserSecretKey(String userSecretKey) {
        this.userSecretKey = userSecretKey;
    }

    public long getUserTtl() {
        return userTtl;
    }

    public void setUserTtl(long userTtl) {
        this.userTtl = userTtl;
    }

    public String getUserTokenName() {
        return userTokenName;
    }

    public void setUserTokenName(String userTokenName) {
        this.userTokenName = userTokenName;
    }
}
