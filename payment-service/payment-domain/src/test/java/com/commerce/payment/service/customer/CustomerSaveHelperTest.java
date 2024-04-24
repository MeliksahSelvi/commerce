package com.commerce.payment.service.customer;

import com.commerce.payment.service.account.handler.helper.CustomerSaveHelper;
import com.commerce.payment.service.account.usecase.CustomerSave;
import com.commerce.payment.service.common.exception.PaymentDomainException;
import com.commerce.payment.service.customer.adapter.FakeCustomerDataAdapter;
import com.commerce.payment.service.customer.adapter.FakeEncryptingAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author mselvi
 * @Created 24.04.2024
 */

class CustomerSaveHelperTest {

    CustomerSaveHelper customerSaveHelper;

    @BeforeEach
    void setUp() {
        customerSaveHelper = new CustomerSaveHelper(new FakeCustomerDataAdapter(), new FakeEncryptingAdapter());
    }

    @Test
    void should_save() {
        //given
        var customerSave = new CustomerSave(null,"Ali", "Demir", "1234567890", "email2@com", "123");

        //when
        var customer = customerSaveHelper.save(customerSave);

        //then
        assertEquals(customerSave.firstName(), customer.getFirstName());
        assertEquals(customerSave.lastName(), customer.getLastName());
        assertEquals(customerSave.identityNo(), customer.getIdentityNo());
        assertEquals(customerSave.email(), customer.getEmail());
        assertNotEquals(customerSave.password(), customer.getPassword());
        assertNull(customer.getId());
    }

    @Test
    void should_save_fail_when_customer_not_unique() {
        //given
        var customerSave = new CustomerSave(null,"Ali", "Demir", "123456789", "email@com", "123");
        var errorMessage="Email or Identity No must be unique!";

        //when
        //then
        var exception = assertThrows(PaymentDomainException.class, () -> customerSaveHelper.save(customerSave));
        assertTrue(exception.getMessage().contains(errorMessage));
    }
}
