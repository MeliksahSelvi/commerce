package com.commerce.notification.service.customer.adapters.messaging.adapter;

import com.commerce.notification.service.customer.model.Customer;
import com.commerce.notification.service.customer.port.jpa.CustomerDataPort;
import com.commerce.notification.service.customer.usecase.CustomerRetrieve;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 26.04.2024
 */

public class FakeCustomerDataAdapter implements CustomerDataPort {

    private static final Long EXIST_CUSTOMER_ID = 1L;

    @Override
    public Optional<Customer> findById(CustomerRetrieve customerRetrieve) {
        if (customerRetrieve.customerId() != EXIST_CUSTOMER_ID) {
            return Optional.empty();
        }
        return Optional.of(Customer.builder()
                .id(EXIST_CUSTOMER_ID)
                .firstName("name1")
                .lastName("surname1")
                .email("email1")
                .build());
    }

    @Override
    public Customer save(Customer customer) {
        return Customer.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .build();
    }
}
