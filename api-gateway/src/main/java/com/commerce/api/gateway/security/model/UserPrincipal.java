package com.commerce.api.gateway.security.model;

import java.io.Serializable;

/**
 * @Author mselvi
 * @Created 01.04.2024
 */

public record UserPrincipal(String email, Long userId,Long roleId) implements Serializable {
}
