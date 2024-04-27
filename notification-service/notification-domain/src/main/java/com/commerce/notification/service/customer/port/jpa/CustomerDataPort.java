package com.commerce.notification.service.customer.port.jpa;

import com.commerce.notification.service.customer.model.Customer;
import com.commerce.notification.service.customer.usecase.CustomerRetrieve;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 26.04.2024
 */

public interface CustomerDataPort {

    Optional<Customer> findById(CustomerRetrieve customerRetrieve);

    Customer save(Customer customer);

}
