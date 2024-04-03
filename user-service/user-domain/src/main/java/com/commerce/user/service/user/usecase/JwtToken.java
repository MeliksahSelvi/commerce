package com.commerce.user.service.user.usecase;

import com.commerce.user.service.common.model.UseCase;

import java.io.Serializable;

/**
 * @Author mselvi
 * @Created 31.03.2024
 */

public record JwtToken(Long userId, String token, Long tokenIssuedTime) implements UseCase, Serializable {
}
