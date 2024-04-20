package com.commerce.customer.service.customer.usecase;

import com.commerce.customer.service.common.model.UseCase;

/**
 * @Author mselvi
 * @Created 15.04.2024
 */

public record CustomerDelete(Long customerId) implements UseCase {
}
