package com.commerce.customer.service.adapters.customer.customer.rest.dto;

import com.commerce.customer.service.customer.entity.Customer;

/**
 * @Author mselvi
 * @Created 10.03.2024
 */

public record CustomerResponse(Long id, String firstName, String lastName, String identityNo, String email) {

    public CustomerResponse(Customer customer) {
        this(customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getIdentityNo(), customer.getEmail());
    }
}
