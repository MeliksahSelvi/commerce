package com.commerce.customer.service.handler;

import com.commerce.customer.service.common.exception.CustomerDomainException;
import com.commerce.customer.service.customer.handler.helper.CustomerSaveHelper;
import com.commerce.customer.service.customer.usecase.CustomerSave;
import com.commerce.customer.service.handler.adapter.FakeCustomerDataAdapter;
import com.commerce.customer.service.handler.adapter.FakeEncrptingAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author mselvi
 * @Created 20.03.2024
 */

class CustomerSaveHelperTest {

    CustomerSaveHelper customerSaveHelper;

    @BeforeEach
    void setUp() {
        customerSaveHelper = new CustomerSaveHelper(new FakeCustomerDataAdapter(), new FakeEncrptingAdapter());
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
        var exception = assertThrows(CustomerDomainException.class, () -> customerSaveHelper.save(customerSave));
        assertTrue(exception.getMessage().contains(errorMessage));
    }
}
