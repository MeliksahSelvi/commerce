package com.commerce.payment.service.account.port.jpa;

import com.commerce.payment.service.account.entity.Customer;
import com.commerce.payment.service.account.usecase.CustomerDelete;
import com.commerce.payment.service.account.usecase.CustomerRetrieve;
import com.commerce.payment.service.account.usecase.CustomerRetrieveAll;

import java.util.List;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 24.04.2024
 */

public interface CustomerDataPort {

    List<Customer> findAll(CustomerRetrieveAll customerRetrieveAll);

    Optional<Customer> findById(CustomerRetrieve customerRetrieve);

    Customer save(Customer customer);

    Optional<Customer> findByEmailOrIdentityNo(String email, String identityNo);

    void deleteById(CustomerDelete customerDelete);
}
