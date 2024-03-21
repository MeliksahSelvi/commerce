package com.commerce.payment.service;

import com.commerce.payment.service.adapter.FakeAccountDataAdapter;
import com.commerce.payment.service.payment.handler.AccountRetrieveAllUseCaseHandler;
import com.commerce.payment.service.payment.usecase.AccountRetrieveAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

class AccountRetrieveAllUseCaseHandlerTest {

    AccountRetrieveAllUseCaseHandler accountRetrieveAllUseCaseHandler;

    @BeforeEach
    void setUp(){
        accountRetrieveAllUseCaseHandler=new AccountRetrieveAllUseCaseHandler(new FakeAccountDataAdapter());
    }

    @Test
    void should_retrieveAll(){
        //given
        var accountRetrieveAll = new AccountRetrieveAll(Optional.of(0), Optional.of(3));

        //when
        var accountList = accountRetrieveAllUseCaseHandler.handle(accountRetrieveAll);

        //then
        assertEquals(accountRetrieveAll.size().get(),accountList.size());
        assertNotEquals(Collections.EMPTY_LIST.size(),accountList.size());
    }

    @Test
    void should_retrieveAll_empty(){
        //given
        var accountRetrieveAll = new AccountRetrieveAll(Optional.of(0), Optional.of(0));

        //when
        var accountList = accountRetrieveAllUseCaseHandler.handle(accountRetrieveAll);

        //then
        assertEquals(accountRetrieveAll.size().get(),accountList.size());
        assertEquals(Collections.EMPTY_LIST.size(),accountList.size());
    }
}
