package com.commerce.payment.service.adapters.customer.rest.dto;

import com.commerce.payment.service.customer.entity.Customer;

/**
 * @Author mselvi
 * @Created 24.04.2024
 */

public record CustomerResponse(Long id, String firstName, String lastName, String identityNo, String email) {

    public CustomerResponse(Customer customer) {
        this(customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getIdentityNo(), customer.getEmail());
    }
}
