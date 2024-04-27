package com.commerce.order.query.service.common.model;

import java.io.Serializable;

/**
 * @Author mselvi
 * @Created 27.04.2024
 */

public record UserPrincipal(String email, Long userId,Long roleId) implements Serializable {
}
