package com.commerce.payment.service.account.usecase;

import com.commerce.payment.service.common.model.UseCase;

/**
 * @Author mselvi
 * @Created 24.04.2024
 */

public record CustomerDelete(Long customerId) implements UseCase {
}
