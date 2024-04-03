package com.commerce.api.gateway.security.filter;

import com.commerce.api.gateway.cache.RedisCacheClient;
import com.commerce.api.gateway.security.model.JwtToken;
import com.commerce.api.gateway.security.model.UserPrincipal;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @Author mselvi
 * @Created 28.03.2024
 */

@Component
public class JwtTokenValidator {

    @Value("${commerce.jwt.security.key}")
    private String TOKEN_KEY;

    private final RedisCacheClient redisCacheClient;
    private final ObjectMapper objectMapper;

    public JwtTokenValidator(RedisCacheClient redisCacheClient, ObjectMapper objectMapper) {
        this.redisCacheClient = redisCacheClient;
        this.objectMapper = objectMapper;
    }

    public boolean isNotExistOnCache(String token, String userPrincipalAsJson) {
        UserPrincipal userPrincipal = extractDataFromJson(userPrincipalAsJson, UserPrincipal.class);

        String jwtTokenAsJson = redisCacheClient.get(userPrincipal.userId());

        JwtToken jwtTokenFromCache = extractDataFromJson(jwtTokenAsJson, JwtToken.class);
        return !token.equals(jwtTokenFromCache.token());
    }

    public String getSubjectFromToken(String token) {
        return Jwts.parser().setSigningKey(TOKEN_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    private <T extends Serializable> T extractDataFromJson(String payload, Class<T> outputType) {
        try {
            return objectMapper.readValue(payload, outputType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(String.format("Could not read %s object! %s", outputType.getName()), e);
        }
    }
}
