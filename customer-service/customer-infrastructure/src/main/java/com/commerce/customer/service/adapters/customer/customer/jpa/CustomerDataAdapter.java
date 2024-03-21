package com.commerce.customer.service.adapters.customer.customer.jpa;

import com.commerce.customer.service.adapters.customer.customer.jpa.entity.CustomerEntity;
import com.commerce.customer.service.adapters.customer.customer.jpa.repository.CustomerEntityRepository;
import com.commerce.customer.service.customer.entity.Customer;
import com.commerce.customer.service.customer.port.jpa.CustomerDataPort;
import com.commerce.customer.service.customer.usecase.CustomerRetrieve;
import com.commerce.customer.service.customer.usecase.CustomerRetrieveAll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 10.03.2024
 */

@Service
public class CustomerDataAdapter implements CustomerDataPort {

    private static final Integer DEFAULT_PAGE = 0;
    private static final Integer DEFAULT_SIZE = 10;
    private final CustomerEntityRepository customerEntityRepository;

    public CustomerDataAdapter(CustomerEntityRepository customerEntityRepository) {
        this.customerEntityRepository = customerEntityRepository;
    }

    @Override
    public List<Customer> findAll(CustomerRetrieveAll customerRetrieveAll) {
        PageRequest pageRequest = PageRequest.of(customerRetrieveAll.page().orElse(DEFAULT_PAGE), customerRetrieveAll.size().orElse(DEFAULT_SIZE));
        Page<CustomerEntity> customerEntityPage = customerEntityRepository.findAll(pageRequest);
        return customerEntityPage.stream().map(CustomerEntity::toModel).toList();
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
        customerEntity.setIdentityNo(customer.getIdentityNo());
        customerEntity.setEmail(customer.getEmail());
        customerEntity.setPassword(customer.getPassword());
        return customerEntityRepository.save(customerEntity).toModel();
    }
}
