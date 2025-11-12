package com.laolao.common.config;


import com.laolao.common.handler.JsonTypeHandler;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis 类型处理器配置类
 * 仅用于注册自定义类型处理器
 */
@Configuration
public class MybatisConfig {
    /**
     * 注册 JsonTypeHandler 用于处理 List<String> 与 JSON 的转换
     */
    @Bean
    public ConfigurationCustomizer typeHandlerCustomizer() {
        return configuration -> {
            // 注册 JsonTypeHandler
            configuration.getTypeHandlerRegistry().register(JsonTypeHandler.class);
        };
    }
}
