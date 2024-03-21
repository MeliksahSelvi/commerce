package com.commerce.customer.service.handler;

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
    void should_handle() {
        //given
        var customerSave = new CustomerSave("Ali", "Demir", "123456789", "email@com", "123");

        //when
        var customer = customerSaveHelper.handle(customerSave);

        //then
        assertEquals(customerSave.firstName(), customer.getFirstName());
        assertEquals(customerSave.lastName(), customer.getLastName());
        assertEquals(customerSave.identityNo(), customer.getIdentityNo());
        assertEquals(customerSave.email(), customer.getEmail());
        assertNotEquals(customerSave.password(), customer.getPassword());
        assertNull(customer.getId());
    }
}
