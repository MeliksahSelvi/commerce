package com.commerce.payment.service.customer;

import ch.qos.logback.classic.Level;
import com.commerce.payment.service.account.handler.CustomerSaveUseCaseHandler;
import com.commerce.payment.service.account.usecase.CustomerSave;
import com.commerce.payment.service.common.LoggerTest;
import com.commerce.payment.service.common.exception.PaymentDomainException;
import com.commerce.payment.service.customer.adapter.FakeCustomerSaveHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @Author mselvi
 * @Created 24.04.2024
 */

class CustomerSaveUseCaseHandlerTest extends LoggerTest<CustomerSaveUseCaseHandler> {

    CustomerSaveUseCaseHandler handler;

    public CustomerSaveUseCaseHandlerTest() {
        super(CustomerSaveUseCaseHandler.class);
    }

    @BeforeEach
    void setUp() {
        handler = new CustomerSaveUseCaseHandler(new FakeCustomerSaveHelper());
    }

    @AfterEach
    protected void cleanUp() {
        cleanUpActions();
    }

    @Test
    void should_save() {
        //given
        var customerSave = new CustomerSave(null,"Ali", "Demir", "1234567890", "email2@com", "123");
        var logMessage="Customer save action started by email: email2@com";

        //when
        var customer = handler.handle(customerSave);

        //then
        assertEquals(customerSave.firstName(), customer.getFirstName());
        assertEquals(customerSave.lastName(), customer.getLastName());
        assertEquals(customerSave.identityNo(), customer.getIdentityNo());
        assertEquals(customerSave.email(), customer.getEmail());
        assertNotEquals(customerSave.password(), customer.getPassword());
        assertNull(customer.getId());
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_save_fail_when_customer_not_unique() {
        //given
        var customerSave = new CustomerSave(null,"Ali", "Demir", "123456789", "email@com", "123");
        var logMessage="Customer save action started by email: email@com";
        var errorMessage="Email or Identity No must be unique!";

        //when
        //then
        var exception = assertThrows(PaymentDomainException.class, () -> handler.handle(customerSave));
        assertTrue(exception.getMessage().contains(errorMessage));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }
}

