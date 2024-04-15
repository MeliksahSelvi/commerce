package com.commerce.payment.service.payment.usecase;

import com.commerce.payment.service.common.model.UseCase;
import com.commerce.payment.service.common.valueobject.CurrencyType;
import com.commerce.payment.service.common.valueobject.Money;

/**
 * @Author mselvi
 * @Created 12.03.2024
 */

public record AccountSave(Long accountId,Long customerId, Money currentBalance, CurrencyType currencyType) implements UseCase {
}
