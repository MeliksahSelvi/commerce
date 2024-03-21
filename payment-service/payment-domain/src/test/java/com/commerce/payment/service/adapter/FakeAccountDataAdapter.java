package com.commerce.payment.service.adapter;

import com.commerce.payment.service.common.valueobject.CurrencyType;
import com.commerce.payment.service.common.valueobject.Money;
import com.commerce.payment.service.payment.entity.Account;
import com.commerce.payment.service.payment.port.jpa.AccountDataPort;
import com.commerce.payment.service.payment.usecase.AccountRetrieve;
import com.commerce.payment.service.payment.usecase.AccountRetrieveAll;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

public class FakeAccountDataAdapter implements AccountDataPort {

    private static final Long EXIST_CUSTOMER_ID = 1L;
    private static final Long EXIST_ACCOUNT_ID = 1L;

    @Override
    public Optional<Account> findByCustomerId(Long customerId) {
        if (EXIST_CUSTOMER_ID!=customerId){
            return Optional.empty();
        }
        return Optional.of(
                Account.builder()
                .id(EXIST_ACCOUNT_ID)
                .customerId(customerId)
                .cancelDate(null)
                .currencyType(CurrencyType.TL)
                .currentBalance(new Money(BigDecimal.valueOf(100.00)))
                .ibanNo("iban")
                .build()
        );
    }

    @Override
    public Optional<Account> findById(AccountRetrieve accountRetrieve) {
        if (EXIST_ACCOUNT_ID!=accountRetrieve.accountId()){
            return Optional.empty();
        }
        return Optional.of(
                Account.builder()
                        .id(EXIST_ACCOUNT_ID)
                        .customerId(EXIST_CUSTOMER_ID)
                        .cancelDate(null)
                        .currencyType(CurrencyType.TL)
                        .currentBalance(new Money(BigDecimal.valueOf(100.00)))
                        .ibanNo("iban")
                        .build()
        );
    }

    @Override
    public Account save(Account account) {
        return Account.builder()
                .id(account.getId())
                .customerId(account.getCustomerId())
                .ibanNo(account.getIbanNo())
                .currentBalance(account.getCurrentBalance())
                .currencyType(account.getCurrencyType())
                .cancelDate(account.getCancelDate())
                .build();
    }

    @Override
    public List<Account> findAll(AccountRetrieveAll accountRetrieveAll) {
        if (accountRetrieveAll.size().get() == 0) {
            return Collections.emptyList();
        }
        return List.of(
                Account.builder()
                        .id(1L)
                        .customerId(1L)
                        .cancelDate(null)
                        .currencyType(CurrencyType.TL)
                        .currentBalance(new Money(BigDecimal.valueOf(100.00)))
                        .ibanNo("iban")
                        .build(),
                Account.builder()
                        .id(2L)
                        .customerId(2L)
                        .cancelDate(null)
                        .currencyType(CurrencyType.TL)
                        .currentBalance(new Money(BigDecimal.valueOf(100.00)))
                        .ibanNo("iban")
                        .build(),
                Account.builder()
                        .id(3L)
                        .customerId(3L)
                        .cancelDate(LocalDateTime.now().minusYears(1))
                        .currencyType(CurrencyType.TL)
                        .currentBalance(new Money(BigDecimal.valueOf(100.00)))
                        .ibanNo("iban")
                        .build()
        );
    }
}
