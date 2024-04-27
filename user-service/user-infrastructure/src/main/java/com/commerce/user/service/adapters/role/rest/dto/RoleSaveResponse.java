package com.commerce.user.service.adapters.role.rest.dto;

import com.commerce.user.service.common.valueobject.RoleType;
import com.commerce.user.service.role.model.Role;

/**
 * @Author mselvi
 * @Created 01.04.2024
 */

public record RoleSaveResponse(Long roleId, RoleType roleType, String permissions) {

    public RoleSaveResponse(Role role) {
        this(role.getId(),role.getRoleType(),role.getPermissions());
    }
}
