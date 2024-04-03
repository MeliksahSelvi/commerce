package com.commerce.user.service.role.usecase;

import com.commerce.user.service.common.model.UseCase;
import com.commerce.user.service.common.valueobject.RoleType;

/**
 * @Author mselvi
 * @Created 01.04.2024
 */

public record RoleSave(Long roleId, RoleType roleType, String permissions) implements UseCase {
}
