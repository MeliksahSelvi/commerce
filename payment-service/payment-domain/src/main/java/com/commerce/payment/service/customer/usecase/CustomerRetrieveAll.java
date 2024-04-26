package com.commerce.payment.service.customer.usecase;

import com.commerce.payment.service.common.model.UseCase;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 24.04.2024
 */

public record CustomerRetrieveAll(Optional<Integer> page, Optional<Integer> size) implements UseCase {
}
