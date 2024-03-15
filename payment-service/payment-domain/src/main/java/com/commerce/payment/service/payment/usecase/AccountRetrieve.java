package com.commerce.payment.service.payment.usecase;

import com.commerce.payment.service.common.model.UseCase;

/**
 * @Author mselvi
 * @Created 12.03.2024
 */

public record AccountRetrieve(Long accountId) implements UseCase {
}
