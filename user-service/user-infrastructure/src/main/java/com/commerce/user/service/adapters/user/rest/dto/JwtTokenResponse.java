package com.commerce.user.service.adapters.user.rest.dto;

import com.commerce.user.service.user.usecase.JwtToken;

/**
 * @Author mselvi
 * @Created 31.03.2024
 */

public record JwtTokenResponse(Long userId, String token, Long tokenIssuedTime) {

    public JwtTokenResponse(JwtToken jwtToken) {
        this(jwtToken.userId(),jwtToken.token(), jwtToken.tokenIssuedTime());
    }
}
