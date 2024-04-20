package com.commerce.customer.service.customer.port.jpa;

import com.commerce.customer.service.customer.entity.Customer;
import com.commerce.customer.service.customer.usecase.CustomerDelete;
import com.commerce.customer.service.customer.usecase.CustomerRetrieve;
import com.commerce.customer.service.customer.usecase.CustomerRetrieveAll;

import java.util.List;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 10.03.2024
 */

public interface CustomerDataPort {

    List<Customer> findAll(CustomerRetrieveAll customerRetrieveAll);

    Optional<Customer> findById(CustomerRetrieve customerRetrieve);

    Customer save(Customer customer);

    Optional<Customer> findByEmailAndIdentityNo(String email, String identityNo);

    void deleteById(CustomerDelete customerDelete);
}
