package com.commerce.api.gateway.security.model;

import java.io.Serializable;

/**
 * @Author mselvi
 * @Created 01.04.2024
 */

public record JwtToken(Long userId, String token, Long tokenIssuedTime) implements Serializable {
}
