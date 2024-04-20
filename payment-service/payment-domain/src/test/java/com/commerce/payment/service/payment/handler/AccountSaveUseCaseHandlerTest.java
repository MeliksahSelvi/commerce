package com.commerce.payment.service.payment.handler;

import ch.qos.logback.classic.Level;
import com.commerce.payment.service.adapter.FakeAccountSaveHelper;
import com.commerce.payment.service.common.LoggerTest;
import com.commerce.payment.service.common.exception.PaymentDomainException;
import com.commerce.payment.service.common.valueobject.CurrencyType;
import com.commerce.payment.service.common.valueobject.Money;
import com.commerce.payment.service.payment.usecase.AccountSave;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

class AccountSaveUseCaseHandlerTest extends LoggerTest<AccountSaveUseCaseHandler> {

    AccountSaveUseCaseHandler accountSaveUseCaseHandler;

    public AccountSaveUseCaseHandlerTest() {
        super(AccountSaveUseCaseHandler.class);
    }

    @BeforeEach
    void setUp() {
        accountSaveUseCaseHandler = new AccountSaveUseCaseHandler(new FakeAccountSaveHelper());
    }

    @AfterEach
    @Override
    protected void cleanUp() {
        cleanUpActions();
    }

    @Test
    void should_save() {
        //given
        var accountSave = new AccountSave(null, 1L, new Money(BigDecimal.valueOf(1000)), CurrencyType.TL);
        var logMessage = "Account save action started by customerId: 1";

        //when
        //then
        var account = assertDoesNotThrow(() -> accountSaveUseCaseHandler.handle(accountSave));
        assertEquals(accountSave.customerId(), account.getCustomerId());
        assertEquals(accountSave.currencyType(), account.getCurrencyType());
        assertEquals(accountSave.currentBalance(), account.getCurrentBalance());
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_save_fail_when_customer_is_not_exist() {
        //given
        var accountSave = new AccountSave(1L, 2L, new Money(BigDecimal.valueOf(1000)), CurrencyType.TL);
        var logMessage = "Account save action started by customerId: 2";
        var errorMessage="Customer could not found for account save operation";

        //when
        //then
        var exception = assertThrows(PaymentDomainException.class, () -> accountSaveUseCaseHandler.handle(accountSave));
        assertTrue(exception.getMessage().contains(errorMessage));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }
}
