package com.commerce.notification.service.customer.usecase;

import com.commerce.notification.service.common.model.UseCase;

/**
 * @Author mselvi
 * @Created 26.04.2024
 */

public record CustomerRetrieve(Long customerId) implements UseCase {
}
