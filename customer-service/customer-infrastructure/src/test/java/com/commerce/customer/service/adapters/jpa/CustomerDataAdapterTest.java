package com.commerce.customer.service.adapters.jpa;

import com.commerce.customer.service.adapters.jpa.entity.CustomerEntity;
import com.commerce.customer.service.adapters.jpa.repository.CustomerEntityRepository;
import com.commerce.customer.service.customer.usecase.CustomerRetrieve;
import com.commerce.customer.service.customer.usecase.CustomerRetrieveAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * @Author mselvi
 * @Created 20.03.2024
 */

@ExtendWith(MockitoExtension.class)
public class CustomerDataAdapterTest {

    @InjectMocks
    private CustomerDataAdapter customerDataAdapter;

    @Mock
    private CustomerEntityRepository customerEntityRepository;

    @Test
    void should_findAll() {
        //given
        CustomerRetrieveAll customerRetrieveAll = new CustomerRetrieveAll(Optional.of(0), Optional.of(1));
        PageRequest pageRequest = PageRequest.of(customerRetrieveAll.page().get(), customerRetrieveAll.size().get());
        Page<CustomerEntity> page = new PageImpl<>(buildCustomerEntities());
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
        CustomerRetrieveAll customerRetrieveAll = new CustomerRetrieveAll(Optional.of(0), Optional.of(1));
        PageRequest pageRequest = PageRequest.of(customerRetrieveAll.page().get(), customerRetrieveAll.size().get());
        Page<CustomerEntity> page = new PageImpl<>(Collections.emptyList());
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
        CustomerRetrieve retrieve = new CustomerRetrieve(1L);
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
        CustomerRetrieve retrieve = new CustomerRetrieve(1L);
        when(customerEntityRepository.findById(retrieve.customerId())).thenReturn(Optional.empty());

        //when
        var customerOptional = customerDataAdapter.findById(retrieve);

        //then
        assertTrue(customerOptional.isEmpty());
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

}
