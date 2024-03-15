package com.commerce.payment.service.payment.usecase;

import com.commerce.payment.service.common.model.UseCase;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 12.03.2024
 */

public record AccountRetrieveAll(Optional<Integer> page, Optional<Integer> size) implements UseCase {
}
