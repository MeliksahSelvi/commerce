package com.commerce.customer.service.handler.adapter;

import com.commerce.customer.service.customer.entity.Customer;
import com.commerce.customer.service.customer.port.jpa.CustomerDataPort;
import com.commerce.customer.service.customer.usecase.CustomerRetrieve;
import com.commerce.customer.service.customer.usecase.CustomerRetrieveAll;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 20.03.2024
 */

public class FakeCustomerDataPort implements CustomerDataPort {

    private static final Long EXIST_CUSTOMER_ID = 1L;

    @Override
    public List<Customer> findAll(CustomerRetrieveAll customerRetrieveAll) {
        if (customerRetrieveAll.size().get() == 0) {
            return Collections.emptyList();
        }
        return List.of(
                Customer.builder()
                        .id(1L)
                        .firstName("name1")
                        .lastName("surname1")
                        .identityNo("identity1")
                        .email("email1")
                        .password("password1")
                        .build(),
                Customer.builder()
                        .id(2L)
                        .firstName("name2")
                        .lastName("surname2")
                        .identityNo("identity2")
                        .email("email2")
                        .password("password2")
                        .build(),
                Customer.builder()
                        .id(3L)
                        .firstName("name3")
                        .lastName("surname3")
                        .identityNo("identity3")
                        .email("email3")
                        .password("password3")
                        .build());
    }

    @Override
    public Optional<Customer> findById(CustomerRetrieve customerRetrieve) {
        if (customerRetrieve.customerId() != EXIST_CUSTOMER_ID) {
            return Optional.empty();
        }
        return Optional.of(Customer.builder()
                .id(EXIST_CUSTOMER_ID)
                .firstName("name1")
                .lastName("surname1")
                .identityNo("identity1")
                .email("email1")
                .password("password1")
                .build());
    }

    @Override
    public Customer save(Customer customer) {
        return Customer.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .identityNo(customer.getIdentityNo())
                .email(customer.getEmail())
                .password(customer.getEmail())
                .build();
    }

    @Override
    public boolean existById(Long customerId) {
        return customerId == EXIST_CUSTOMER_ID;
    }
}
