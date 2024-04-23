package com.commerce.user.service.adapters.user.cache;

import com.commerce.user.service.common.exception.UserDomainException;
import com.commerce.user.service.user.port.cache.TokenCachePort;
import com.commerce.user.service.user.usecase.JwtToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 31.03.2024
 */

@Service
@Profile("!test")
public class RedisAdapter implements TokenCachePort {

    @Value("${commerce.jwt.security.key}")
    private String TOKEN_KEY;

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public RedisAdapter(RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }


    @Override
    public void put(Long userId, JwtToken jwtToken) {
        String jwtTokenAsJson = convertDataToJson(jwtToken);
        redisTemplate.opsForHash().put(TOKEN_KEY, userId, jwtTokenAsJson);
    }

    @Override
    public Optional<JwtToken> get(Long userId) {
        String jwtTokenAsJson = (String) redisTemplate.opsForHash().get(TOKEN_KEY, userId);
        JwtToken jwtToken = exractDataFromJson(jwtTokenAsJson);
        return Optional.ofNullable(jwtToken);
    }

    @Override
    public void remove(Long userId) {
        redisTemplate.opsForHash().delete(TOKEN_KEY, userId);
    }

    private String convertDataToJson(JwtToken jwtToken) {
        try {
            return objectMapper.writeValueAsString(jwtToken);
        } catch (JsonProcessingException e) {
            throw new UserDomainException("Could not create JwtToken object", e);
        }
    }

    private JwtToken exractDataFromJson(String strAsJson) {
        try {
            return objectMapper.readValue(strAsJson, JwtToken.class);
        } catch (JsonProcessingException e) {
            throw new UserDomainException("Could not read JwtToken object", e);
        }
    }
}
