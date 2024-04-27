package com.commerce.notification.service.adapters.customer.jpa;

import com.commerce.notification.service.adapters.customer.jpa.entity.CustomerEntity;
import com.commerce.notification.service.adapters.customer.jpa.repository.CustomerEntityRepository;
import com.commerce.notification.service.customer.model.Customer;
import com.commerce.notification.service.customer.port.jpa.CustomerDataPort;
import com.commerce.notification.service.customer.usecase.CustomerRetrieve;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 26.04.2024
 */

@Service
public class CustomerDataAdapter implements CustomerDataPort {

    private final CustomerEntityRepository customerEntityRepository;

    public CustomerDataAdapter(CustomerEntityRepository customerEntityRepository) {
        this.customerEntityRepository = customerEntityRepository;
    }

    @Override
    public Optional<Customer> findById(CustomerRetrieve customerRetrieve) {
        Optional<CustomerEntity> customerEntityOptional = customerEntityRepository.findById(customerRetrieve.customerId());
        return customerEntityOptional.map(CustomerEntity::toModel);
    }

    @Override
    public Customer save(Customer customer) {
        var customerEntity = new CustomerEntity();
        customerEntity.setId(customer.getId());
        customerEntity.setFirstName(customer.getFirstName());
        customerEntity.setLastName(customer.getLastName());
        customerEntity.setEmail(customer.getEmail());
        return customerEntityRepository.save(customerEntity).toModel();
    }
}
