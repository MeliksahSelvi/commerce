package com.commerce.user.service.common.security.principal;

import java.io.Serializable;

/**
 * @Author mselvi
 * @Created 31.03.2024
 */

public record UserPrincipal(String email, Long userId,Long roleId) implements Serializable {
}
