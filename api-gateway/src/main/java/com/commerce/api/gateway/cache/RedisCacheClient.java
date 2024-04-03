package com.commerce.api.gateway.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author mselvi
 * @Created 01.04.2024
 */

@Component
public class RedisCacheClient {

    @Value("${commerce.jwt.security.key}")
    private String TOKEN_KEY;

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisCacheClient(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String get(Long userId) {
        return (String) redisTemplate.opsForHash().get(TOKEN_KEY, userId);
    }
}
