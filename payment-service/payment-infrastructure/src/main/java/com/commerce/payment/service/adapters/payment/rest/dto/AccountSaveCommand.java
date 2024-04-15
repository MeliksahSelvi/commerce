package com.commerce.payment.service.adapters.payment.rest.dto;

import com.commerce.payment.service.common.valueobject.CurrencyType;
import com.commerce.payment.service.common.valueobject.Money;
import com.commerce.payment.service.payment.usecase.AccountSave;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

/**
 * @Author mselvi
 * @Created 12.03.2024
 */

public record AccountSaveCommand(Long accountId,@NotNull @Positive Long customerId, @NotNull @Positive BigDecimal currentBalance,
                                 @NotNull CurrencyType currencyType) {

    public AccountSave toModel() {
        return new AccountSave(accountId,customerId, new Money(currentBalance), currencyType);
    }
}
