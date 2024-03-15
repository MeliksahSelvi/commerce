package com.commerce.payment.service.adapters.payment.rest.dto;

import com.commerce.payment.service.common.valueobject.CurrencyType;
import com.commerce.payment.service.payment.entity.Account;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author mselvi
 * @Created 12.03.2024
 */

public record AccountResponse(Long id, Long customerId, BigDecimal currentBalance, CurrencyType currencyType,
                              String ibanNo, LocalDateTime cancelDate) {

    public AccountResponse(Account account) {
        this(account.getId(), account.getCustomerId(), account.getCurrentBalance().amount(), account.getCurrencyType(), account.getIbanNo(), account.getCancelDate());
    }
}
