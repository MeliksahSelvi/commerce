package com.commerce.payment.service.payment.handler;

import ch.qos.logback.classic.Level;
import com.commerce.payment.service.payment.adapter.FakeAccountDeleteHelper;
import com.commerce.payment.service.common.LoggerTest;
import com.commerce.payment.service.common.exception.AccountNotFoundException;
import com.commerce.payment.service.payment.usecase.AccountDelete;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @Author mselvi
 * @Created 15.04.2024
 */

class AccountDeleteVoidUseCaseHandlerTest extends LoggerTest<AccountDeleteVoidUseCaseHandler> {

    AccountDeleteVoidUseCaseHandler handler;

    public AccountDeleteVoidUseCaseHandlerTest() {
        super(AccountDeleteVoidUseCaseHandler.class);
    }

    @BeforeEach
    void setUp() {
        handler = new AccountDeleteVoidUseCaseHandler(new FakeAccountDeleteHelper());
    }

    @AfterEach
    @Override
    protected void cleanUp() {
        cleanUpActions();
    }

    @Test
    void should_handle() {
        //given
        var accountDelete = new AccountDelete(1L);
        var logMessage="Account delete action started by id: 1";

        //when
        //then
        assertDoesNotThrow(() -> handler.handle(accountDelete));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }


    @Test
    void should_not_handle_when_customer_not_exist(){
        //given
        var accountDelete = new AccountDelete(5L);
        var logMessage="Account delete action started by id: 5";
        var errorMessage="Account could not be found by id: 5";

        //when
        //then
        var exception = assertThrows(AccountNotFoundException.class, () -> handler.handle(accountDelete));
        assertTrue(exception.getMessage().contains(errorMessage));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }
}
