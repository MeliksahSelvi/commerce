package com.commerce.payment.service;

import com.commerce.payment.service.adapter.FakeAccountDataAdapter;
import com.commerce.payment.service.common.exception.AccountNotFoundException;
import com.commerce.payment.service.payment.handler.AccountRetrieveUseCaseHandler;
import com.commerce.payment.service.payment.usecase.AccountRetrieve;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

class AccountRetrieveUseCaseHandlerTest {

    AccountRetrieveUseCaseHandler accountRetrieveUseCaseHandler;

    @BeforeEach
    void setUp(){
        accountRetrieveUseCaseHandler=new AccountRetrieveUseCaseHandler(new FakeAccountDataAdapter());
    }

    @Test
    void should_retrieve(){
        //given
        var accountRetrieve = new AccountRetrieve(1L);

        //when
        //then
        var account = assertDoesNotThrow(() -> accountRetrieveUseCaseHandler.handle(accountRetrieve));
        assertEquals(accountRetrieve.accountId(), account.getId());
        assertNotNull(account);
    }

    @Test
    void should_retrieve_empty() {
        //given
        var customerRetrieve = new AccountRetrieve(0L);

        //when
        //then
        var exception = assertThrows(AccountNotFoundException.class, () -> accountRetrieveUseCaseHandler.handle(customerRetrieve));
        assertTrue(exception.getMessage().contains("Account could not be found"));
    }
}
