package com.commerce.user.service.adapters.role.rest.dto;

import com.commerce.user.service.common.valueobject.RoleType;
import com.commerce.user.service.role.usecase.RoleSave;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * @Author mselvi
 * @Created 01.04.2024
 */

public record RoleSaveCommand(Long roleId, @NotNull RoleType roleType, @NotEmpty String permissions) {

    public RoleSave toModel(){
        return new RoleSave(roleId,roleType,permissions);
    }
}
