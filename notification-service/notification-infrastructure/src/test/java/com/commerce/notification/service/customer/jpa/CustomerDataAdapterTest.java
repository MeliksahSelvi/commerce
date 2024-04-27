package com.commerce.notification.service.customer.jpa;

import com.commerce.notification.service.adapters.customer.jpa.CustomerDataAdapter;
import com.commerce.notification.service.adapters.customer.jpa.entity.CustomerEntity;
import com.commerce.notification.service.adapters.customer.jpa.repository.CustomerEntityRepository;
import com.commerce.notification.service.customer.model.Customer;
import com.commerce.notification.service.customer.usecase.CustomerRetrieve;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @Author mselvi
 * @Created 26.04.2024
 */
@ExtendWith(MockitoExtension.class)
class CustomerDataAdapterTest {

    @InjectMocks
    private CustomerDataAdapter customerDataAdapter;

    @Mock
    private CustomerEntityRepository customerEntityRepository;

    @Test
    void should_findById() {
        //given
        var retrieve = new CustomerRetrieve(1L);
        when(customerEntityRepository.findById(retrieve.customerId())).thenReturn(Optional.of(buildCustomerEntity()));

        //when
        var customerOptional = customerDataAdapter.findById(retrieve);

        //then
        assertTrue(customerOptional.isPresent());
        assertEquals(retrieve.customerId(), customerOptional.get().getId());
    }

    @Test
    void should_findById_empty() {
        //given
        var retrieve = new CustomerRetrieve(1L);
        when(customerEntityRepository.findById(retrieve.customerId())).thenReturn(Optional.empty());

        //when
        var customerOptional = customerDataAdapter.findById(retrieve);

        //then
        assertTrue(customerOptional.isEmpty());
    }

    @Test
    void should_save() {
        //given
        var customer = buildCustomer();
        var customerEntity = mock(CustomerEntity.class);
        when(customerEntityRepository.save(any())).thenReturn(customerEntity);
        when(customerEntity.toModel()).thenReturn(customer);

        //when
        var savedCustomer = customerDataAdapter.save(customer);

        //then
        assertEquals(customer.getId(),savedCustomer.getId());
        assertEquals(customer.getFirstName(),savedCustomer.getFirstName());
        assertEquals(customer.getLastName(),savedCustomer.getLastName());
        assertEquals(customer.getEmail(),savedCustomer.getEmail());
    }

    private CustomerEntity buildCustomerEntity() {
        var customerEntity = new CustomerEntity();
        customerEntity.setId(1L);
        customerEntity.setFirstName("first1");
        customerEntity.setLastName("last1");
        customerEntity.setEmail("email1");
        return customerEntity;
    }

    private Customer buildCustomer() {
        return Customer.builder()
                .id(1L)
                .email("email1")
                .firstName("first1")
                .lastName("last1")
                .build();
    }
}
