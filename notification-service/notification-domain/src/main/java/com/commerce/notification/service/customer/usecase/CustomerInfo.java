package com.commerce.notification.service.customer.usecase;

import com.commerce.notification.service.common.model.UseCase;

/**
 * @Author mselvi
 * @Created 14.03.2024
 */

public record CustomerInfo(Long id, String firstName, String lastName, String email) implements UseCase {
}
