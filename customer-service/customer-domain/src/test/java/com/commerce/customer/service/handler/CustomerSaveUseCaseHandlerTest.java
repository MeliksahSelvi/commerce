package com.commerce.customer.service.handler;

import com.commerce.customer.service.customer.handler.CustomerSaveUseCaseHandler;
import com.commerce.customer.service.customer.usecase.CustomerSave;
import com.commerce.customer.service.handler.adapter.FakeCustomerSaveHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author mselvi
 * @Created 20.03.2024
 */

class CustomerSaveUseCaseHandlerTest {

    CustomerSaveUseCaseHandler customerSaveUseCaseHandler;

    @BeforeEach
    void setUp() {
        customerSaveUseCaseHandler = new CustomerSaveUseCaseHandler(new FakeCustomerSaveHelper());
    }

    @Test
    void should_save() {
        //given
        var customerSave = new CustomerSave(null,"Ali", "Demir", "123456789", "email@com", "123");

        //when
        var customer = customerSaveUseCaseHandler.handle(customerSave);

        //then
        assertEquals(customerSave.firstName(), customer.getFirstName());
        assertEquals(customerSave.lastName(), customer.getLastName());
        assertEquals(customerSave.identityNo(), customer.getIdentityNo());
        assertEquals(customerSave.email(), customer.getEmail());
        assertNotEquals(customerSave.password(), customer.getPassword());
        assertNull(customer.getId());
    }
}
