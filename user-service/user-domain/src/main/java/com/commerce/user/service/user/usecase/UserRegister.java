package com.commerce.user.service.user.usecase;

import com.commerce.user.service.common.model.UseCase;
import com.commerce.user.service.common.valueobject.RoleType;

/**
 * @Author mselvi
 * @Created 31.03.2024
 */

public record UserRegister(String email, String password, Long customerId, RoleType roleType) implements UseCase {
}
