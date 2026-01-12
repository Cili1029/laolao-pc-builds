package com.laolao.common.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.Duration;

@Configuration
@EnableCaching
public class RedisConfig {

    /**
     * 核心配置：自定义 CacheManager
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        // 初始化 JSON 序列化器
        GenericJackson2JsonRedisSerializer jsonSerializer = getJsonSerializer();

        // 配置默认规则：键用 String，值用 JSON，默认过期 12 小时
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(12))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer))
                .disableCachingNullValues(); // 不缓存 null 值

        // 返回自定义的 Manager
        return new TtlRedisCacheManager(RedisCacheWriter.nonLockingRedisCacheWriter(factory), defaultCacheConfig);
    }

    /**
     * 配置 Jackson 序列化器 (支持时间类型和多态)
     */
    private GenericJackson2JsonRedisSerializer getJsonSerializer() {
        ObjectMapper objectMapper = new ObjectMapper();
        // 设置所有字段可见
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 自动识别 Java 8 时间类型 (LocalDateTime 等)
        objectMapper.registerModule(new JavaTimeModule());
        // 存储类型信息，以便反序列化 (解决 List<User> 泛型擦除问题)
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }

    /**
     * 内部类：支持解析 #ttl 的 CacheManager
     */
    private static class TtlRedisCacheManager extends RedisCacheManager {

        public TtlRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
            super(cacheWriter, defaultCacheConfiguration);
        }

        @Override
        @NonNull
        protected RedisCache createRedisCache(@NonNull String name, @Nullable RedisCacheConfiguration cacheConfig) {
            String cacheName = name;
            // 检查名称中是否包含 #
            if (name.contains("#")) {
                String[] split = name.split("#");
                if (split.length > 1) {
                    try {
                        // 获取过期时间 (秒)
                        long ttl = Long.parseLong(split[1]);
                        // 获取真实的 Key 前缀
                        cacheName = split[0];
                        // 修改过期时间
                        if (cacheConfig != null) {
                            cacheConfig = cacheConfig.entryTtl(Duration.ofMinutes(ttl));
                        }
                    } catch (NumberFormatException e) {
                        // 格式不对就不处理
                    }
                }
            }
            // 调用父类创建
            return super.createRedisCache(cacheName, cacheConfig);
        }
    }
}
