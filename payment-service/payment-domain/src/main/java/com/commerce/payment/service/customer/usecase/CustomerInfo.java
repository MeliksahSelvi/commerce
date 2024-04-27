package com.commerce.payment.service.customer.usecase;

import com.commerce.payment.service.common.model.UseCase;
import com.commerce.payment.service.customer.model.Customer;

/**
 * @Author mselvi
 * @Created 26.04.2024
 */

public record CustomerInfo(Long id, String firstName, String lastName, String email) implements UseCase {

    public CustomerInfo(Customer customer) {
        this(customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getEmail());
    }
}
