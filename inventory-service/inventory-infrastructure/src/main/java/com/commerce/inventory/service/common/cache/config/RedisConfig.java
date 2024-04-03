package com.commerce.inventory.service.common.cache.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;

/**
 * @Author mselvi
 * @Created 20.03.2024
 */

@Configuration
public class RedisConfig {

    private final RedisConfigData redisConfigData;

    public RedisConfig(RedisConfigData redisConfigData) {
        this.redisConfigData = redisConfigData;
    }

    @Bean
    public JedisClientConfiguration getJedisClientConfiguration() {
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jedisPoolingClientConfigurationBuilder = (
                JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();

        GenericObjectPoolConfig genericObjectPoolConfig=new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxTotal(redisConfigData.maxTotal());
        genericObjectPoolConfig.setMaxIdle(redisConfigData.maxIdle());
        genericObjectPoolConfig.setMinIdle(redisConfigData.minIdle());
        return jedisPoolingClientConfigurationBuilder.poolConfig(genericObjectPoolConfig).build();
    }

    @Bean
    public JedisConnectionFactory getJedisConnectionFactory(){
        RedisStandaloneConfiguration redisStandaloneConfiguration=new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisConfigData.host());
        if (StringUtils.hasText(redisConfigData.password())) {
            redisStandaloneConfiguration.setPassword(RedisPassword.of(redisConfigData.password()));
        }
        redisStandaloneConfiguration.setPort(redisConfigData.port());
        return new JedisConnectionFactory(redisStandaloneConfiguration,getJedisClientConfiguration());
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(getJedisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
