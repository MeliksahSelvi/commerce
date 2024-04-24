package com.commerce.payment.service.payment.handler;

import ch.qos.logback.classic.Level;
import com.commerce.payment.service.payment.adapter.FakeAccountDataAdapter;
import com.commerce.payment.service.common.LoggerTest;
import com.commerce.payment.service.common.exception.AccountNotFoundException;
import com.commerce.payment.service.payment.handler.helper.AccountDeleteHelper;
import com.commerce.payment.service.payment.usecase.AccountDelete;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author mselvi
 * @Created 15.04.2024
 */

class AccountDeleteHelperTest extends LoggerTest<AccountDeleteHelper> {

    AccountDeleteHelper deleteHelper;

    public AccountDeleteHelperTest() {
        super(AccountDeleteHelper.class);
    }

    @BeforeEach
    void setUp() {
        deleteHelper = new AccountDeleteHelper(new FakeAccountDataAdapter());
    }

    @AfterEach
    protected void cleanUp() {
        cleanUpActions();
    }

    @Test
    void should_delete() {
        //given
        var accountDelete = new AccountDelete(1L);
        var logMessage = "Account deleted by id: 1";

        //when
        //then
        assertDoesNotThrow(() -> deleteHelper.delete(accountDelete));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_delete_fail_when_customer_not_exist() {
        //given
        var accountDelete = new AccountDelete(5L);
        var errorMessage = "Account could not be found by id: 5";

        //when
        //then
        var exception = assertThrows(AccountNotFoundException.class, () -> deleteHelper.delete(accountDelete));
        assertTrue(exception.getMessage().contains(errorMessage));
    }
}
