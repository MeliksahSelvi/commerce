package com.commerce.payment.service.payment.usecase;

import com.commerce.payment.service.common.model.UseCase;

/**
 * @Author mselvi
 * @Created 15.04.2024
 */

public record AccountDelete(Long accountId) implements UseCase {
}
