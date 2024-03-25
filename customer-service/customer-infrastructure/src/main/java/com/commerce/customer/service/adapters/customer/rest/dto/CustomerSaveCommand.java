package com.commerce.customer.service.adapters.customer.rest.dto;

import com.commerce.customer.service.customer.usecase.CustomerSave;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

/**
 * @Author mselvi
 * @Created 10.03.2024
 */

public record CustomerSaveCommand(@NotEmpty String firstName, @NotEmpty String lastName, @NotEmpty String identityNo,
                                  @Email @NotEmpty String email, @NotEmpty String password) {

    public CustomerSave toModel() {
        return new CustomerSave(firstName, lastName, identityNo, email, password);
    }
}
