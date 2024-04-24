package com.commerce.payment.service.adapters.customer.rest.dto;

import com.commerce.payment.service.account.usecase.CustomerSave;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

/**
 * @Author mselvi
 * @Created 24.04.2024
 */

public record CustomerSaveCommand(Long customerId, @NotEmpty String firstName, @NotEmpty String lastName, @NotEmpty String identityNo,
                                  @Email @NotEmpty String email, @NotEmpty String password) {

    public CustomerSave toModel() {
        return new CustomerSave(customerId,firstName, lastName, identityNo, email, password);
    }
}
