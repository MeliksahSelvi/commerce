package com.commerce.api.gateway.cache.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author mselvi
 * @Created 31.03.2024
 */

@ConfigurationProperties(prefix = "redis")
public record RedisConfigData(String host, String password, Integer port, Integer maxTotal, Integer maxIdle, Integer minIdle) {
}
