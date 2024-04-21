package com.commerce.user.service.role.usecase;

import com.commerce.user.service.common.model.UseCase;
import com.commerce.user.service.common.valueobject.RoleType;

/**
 * @Author mselvi
 * @Created 15.04.2024
 */

public record RoleDelete(Long roleId) implements UseCase {
}
