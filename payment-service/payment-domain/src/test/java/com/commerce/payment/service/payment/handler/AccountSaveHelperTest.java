package com.commerce.payment.service.payment.handler;

import ch.qos.logback.classic.Level;
import com.commerce.payment.service.adapter.FakeAccountDataAdapter;
import com.commerce.payment.service.adapter.FakeAccountSaveHelper;
import com.commerce.payment.service.adapter.FakeInnerRestAdapter;
import com.commerce.payment.service.adapter.FakeRandomGenerateAdapter;
import com.commerce.payment.service.common.LoggerTest;
import com.commerce.payment.service.common.exception.PaymentDomainException;
import com.commerce.payment.service.common.valueobject.CurrencyType;
import com.commerce.payment.service.common.valueobject.Money;
import com.commerce.payment.service.payment.handler.helper.AccountDeleteHelper;
import com.commerce.payment.service.payment.handler.helper.AccountSaveHelper;
import com.commerce.payment.service.payment.usecase.AccountSave;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @Author mselvi
 * @Created 15.04.2024
 */

class AccountSaveHelperTest extends LoggerTest<AccountSaveHelper> {

    AccountSaveHelper accountSaveHelper;

    public AccountSaveHelperTest() {
        super(AccountSaveHelper.class);
    }

    @BeforeEach
    void setUp() {
        accountSaveHelper = new AccountSaveHelper(new FakeRandomGenerateAdapter(),new FakeAccountDataAdapter(),new FakeInnerRestAdapter());
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
        var logMessage = "Account persisted by customerId: 1";

        //when
        //then
        var account = assertDoesNotThrow(() -> accountSaveHelper.save(accountSave));
        assertEquals(accountSave.customerId(), account.getCustomerId());
        assertEquals(accountSave.currencyType(), account.getCurrencyType());
        assertEquals(accountSave.currentBalance(), account.getCurrentBalance());
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_save_fail_when_customer_is_not_exist() {
        //given
        var accountSave = new AccountSave(1L, 2L, new Money(BigDecimal.valueOf(1000)), CurrencyType.TL);
        var errorMessage="Customer could not found for account save operation by customerId: 2";

        //when
        //then
        var exception = assertThrows(PaymentDomainException.class, () -> accountSaveHelper.save(accountSave));
        assertTrue(exception.getMessage().contains(errorMessage));
    }
}
