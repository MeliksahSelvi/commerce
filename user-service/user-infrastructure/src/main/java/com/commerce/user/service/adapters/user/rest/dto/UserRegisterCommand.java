package com.commerce.user.service.adapters.user.rest.dto;

import com.commerce.user.service.common.valueobject.RoleType;
import com.commerce.user.service.user.usecase.UserRegister;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * @Author mselvi
 * @Created 31.03.2024
 */

public record UserRegisterCommand(@NotEmpty @Email String email, @NotEmpty String password,
                                  @Positive Long customerId, @NotNull RoleType roleType) {

    public UserRegister toModel() {
        return new UserRegister(email, password, customerId, roleType);
    }
}
