package com.commerce.user.service.adapters.user.rest.dto;

import com.commerce.user.service.user.usecase.UserLogin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

/**
 * @Author mselvi
 * @Created 31.03.2024
 */

public record UserLoginCommand(@NotEmpty @Email String email, @NotEmpty String password) {

    public UserLogin toModel(){
        return new UserLogin(email,password);
    }
}
