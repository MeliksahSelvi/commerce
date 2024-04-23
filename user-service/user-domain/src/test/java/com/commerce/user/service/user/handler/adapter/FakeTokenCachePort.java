package com.commerce.user.service.user.handler.adapter;

import com.commerce.user.service.user.port.cache.TokenCachePort;
import com.commerce.user.service.user.usecase.JwtToken;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author mselvi
 * @Created 23.04.2024
 */

public class FakeTokenCachePort implements TokenCachePort {

    private Map<Long, JwtToken> tokenMap = new ConcurrentHashMap<>();

    @Override
    public void put(Long userId, JwtToken jwtToken) {
        tokenMap.put(userId,jwtToken);
    }

    @Override
    public Optional<JwtToken> get(Long userId) {
        return Optional.ofNullable(tokenMap.get(userId));
    }

    @Override
    public void remove(Long userId) {
        tokenMap.remove(userId);
    }
}
