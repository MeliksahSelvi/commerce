package com.commerce.user.service.adapters.role.rest.dto;

import com.commerce.user.service.common.valueobject.RoleType;
import com.commerce.user.service.role.model.Role;

/**
 * @Author mselvi
 * @Created 21.04.2024
 */

public record RoleResponse(Long id, RoleType roleType, String permissions) {

    public RoleResponse(Role role) {
        this(role.getId(),role.getRoleType(),role.getPermissions());
    }
}
