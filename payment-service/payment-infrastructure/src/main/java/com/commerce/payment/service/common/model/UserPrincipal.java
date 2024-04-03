package com.commerce.payment.service.common.model;

import java.io.Serializable;

/**
 * @Author mselvi
 * @Created 03.04.2024
 */

public record UserPrincipal(String email, Long userId,Long roleId) implements Serializable {
}
