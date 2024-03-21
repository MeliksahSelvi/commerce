package com.commerce.payment.service;

import com.commerce.payment.service.adapter.FakeAccountDataAdapter;
import com.commerce.payment.service.adapter.FakeInnerRestAdapter;
import com.commerce.payment.service.adapter.FakeRandomGenerateAdapter;
import com.commerce.payment.service.common.exception.PaymentDomainException;
import com.commerce.payment.service.common.valueobject.CurrencyType;
import com.commerce.payment.service.common.valueobject.Money;
import com.commerce.payment.service.payment.handler.AccountSaveUseCaseHandler;
import com.commerce.payment.service.payment.usecase.AccountSave;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

class AccountSaveUseCaseHandlerTest {

    AccountSaveUseCaseHandler accountSaveUseCaseHandler;

    @BeforeEach
    void setUp() {
        accountSaveUseCaseHandler = new AccountSaveUseCaseHandler(new FakeRandomGenerateAdapter(), new FakeAccountDataAdapter(), new FakeInnerRestAdapter());
    }

    @Test
    void should_save() {
        //given
        var accountSave = new AccountSave(1L, new Money(BigDecimal.valueOf(1000)), CurrencyType.TL);

        //when
        //then
        var account = assertDoesNotThrow(() -> accountSaveUseCaseHandler.handle(accountSave));
        assertEquals(accountSave.customerId(), account.getCustomerId());
        assertEquals(accountSave.currencyType(), account.getCurrencyType());
        assertEquals(accountSave.currentBalance(), account.getCurrentBalance());
    }

    @Test
    void should_save_fail_when_customer_is_not_exist() {
        //given
        var accountSave = new AccountSave(2L, new Money(BigDecimal.valueOf(1000)), CurrencyType.TL);


        //when
        //then
        var exception = assertThrows(PaymentDomainException.class, () -> accountSaveUseCaseHandler.handle(accountSave));
        assertTrue(exception.getMessage().contains("Customer could not found for account save operation"));
    }
}
