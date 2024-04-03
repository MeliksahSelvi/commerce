package com.commerce.user.service.user.port.cache;

import com.commerce.user.service.user.usecase.JwtToken;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 31.03.2024
 */

public interface TokenCachePort {

    void put(Long userId, JwtToken jwtToken);

    Optional<JwtToken> get(Long userId);

    void remove(Long userId);
}
