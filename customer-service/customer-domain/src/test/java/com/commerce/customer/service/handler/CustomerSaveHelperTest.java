package com.commerce.customer.service.handler;

import com.commerce.customer.service.customer.handler.helper.CustomerSaveHelper;
import com.commerce.customer.service.customer.usecase.CustomerSave;
import com.commerce.customer.service.handler.adapter.FakeCustomerDataPort;
import com.commerce.customer.service.handler.adapter.FakeEncrptingPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @Author mselvi
 * @Created 20.03.2024
 */

class CustomerSaveHelperTest {

    CustomerSaveHelper customerSaveHelper;

    @BeforeEach
    void setUp() {
        customerSaveHelper = new CustomerSaveHelper(new FakeCustomerDataPort(), new FakeEncrptingPort());
    }

    @Test
    void should_handle() {
        //given
        var customerSave = new CustomerSave("Ali", "Demir", "123456789", "email@com", "123");

        //when
        var customer = customerSaveHelper.handle(customerSave);

        //then
        Assertions.assertEquals(customerSave.firstName(), customer.getFirstName());
        Assertions.assertEquals(customerSave.lastName(), customer.getLastName());
        Assertions.assertEquals(customerSave.identityNo(), customer.getIdentityNo());
        Assertions.assertEquals(customerSave.email(), customer.getEmail());
        Assertions.assertNotEquals(customerSave.password(), customer.getPassword());
    }
}
