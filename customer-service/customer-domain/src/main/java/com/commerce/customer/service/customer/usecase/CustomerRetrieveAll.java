package com.commerce.customer.service.customer.usecase;

import com.commerce.customer.service.common.model.UseCase;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 10.03.2024
 */

public record CustomerRetrieveAll(Optional<Integer> page, Optional<Integer> size) implements UseCase {
}
