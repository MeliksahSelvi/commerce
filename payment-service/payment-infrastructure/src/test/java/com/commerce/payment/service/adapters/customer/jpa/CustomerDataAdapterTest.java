package com.commerce.payment.service.adapters.customer.jpa;

import com.commerce.payment.service.customer.model.Customer;
import com.commerce.payment.service.customer.usecase.CustomerDelete;
import com.commerce.payment.service.customer.usecase.CustomerRetrieve;
import com.commerce.payment.service.customer.usecase.CustomerRetrieveAll;
import com.commerce.payment.service.adapters.customer.jpa.entity.CustomerEntity;
import com.commerce.payment.service.adapters.customer.jpa.repository.CustomerEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @Author mselvi
 * @Created 24.04.2024
 */

@ExtendWith(MockitoExtension.class)
class CustomerDataAdapterTest {

    @InjectMocks
    private CustomerDataAdapter customerDataAdapter;

    @Mock
    private CustomerEntityRepository customerEntityRepository;

    @Test
    void should_findAll() {
        //given
        var customerRetrieveAll = new CustomerRetrieveAll(Optional.of(0), Optional.of(1));
        var pageRequest = PageRequest.of(customerRetrieveAll.page().get(), customerRetrieveAll.size().get());
        var page = new PageImpl<>(buildCustomerEntities());
        when(customerEntityRepository.findAll(pageRequest)).thenReturn(page);

        //when
        var customerList = customerDataAdapter.findAll(customerRetrieveAll);

        //then
        assertEquals(customerList.size(), page.getSize());
        assertEquals(customerRetrieveAll.size().get(), customerList.size());
        assertEquals(customerRetrieveAll.size().get(), page.getSize());
        assertNotEquals(Collections.emptyList().size(), page.getSize());
        assertNotEquals(Collections.emptyList().size(), customerList.size());
    }

    @Test
    void should_findAll_empty() {
        //given
        var customerRetrieveAll = new CustomerRetrieveAll(Optional.of(0), Optional.of(1));
        var pageRequest = PageRequest.of(customerRetrieveAll.page().get(), customerRetrieveAll.size().get());
        var page = new PageImpl<>(new ArrayList<CustomerEntity>());
        when(customerEntityRepository.findAll(pageRequest)).thenReturn(page);

        //when
        var customerList = customerDataAdapter.findAll(customerRetrieveAll);

        //then
        assertEquals(Collections.EMPTY_LIST.size(), customerList.size());
        assertEquals(Collections.EMPTY_LIST.size(), page.getSize());
        assertNotEquals(Collections.EMPTY_LIST.size(), customerRetrieveAll.size());
    }

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
        assertEquals(customer.getIdentityNo(),savedCustomer.getIdentityNo());
        assertEquals(customer.getEmail(),savedCustomer.getEmail());
        assertEquals(customer.getPassword(),savedCustomer.getPassword());
    }

    @Test
    void should_deleteById() {
        //given
        var customerDelete = new CustomerDelete(1L);

        //when
        customerDataAdapter.deleteById(customerDelete);

        //then
        assertEquals(Optional.empty(),customerDataAdapter.findById(new CustomerRetrieve(1L)));
        verify(customerEntityRepository).deleteById(customerDelete.customerId());
    }

    private List<CustomerEntity> buildCustomerEntities() {
        return List.of(buildCustomerEntity());
    }

    private CustomerEntity buildCustomerEntity() {
        var customerEntity = new CustomerEntity();
        customerEntity.setId(1L);
        customerEntity.setFirstName("first1");
        customerEntity.setLastName("last1");
        customerEntity.setIdentityNo("identity1");
        customerEntity.setEmail("email1");
        customerEntity.setPassword("password1");
        return customerEntity;
    }

    private Customer buildCustomer() {
        return Customer.builder()
                .id(1L)
                .identityNo("identity1")
                .email("email1")
                .password("password1")
                .firstName("first1")
                .lastName("last1")
                .build();
    }

}
