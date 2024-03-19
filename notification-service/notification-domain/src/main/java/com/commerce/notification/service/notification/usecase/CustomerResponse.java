package com.commerce.notification.service.notification.usecase;

import com.commerce.notification.service.common.model.UseCase;

/**
 * @Author mselvi
 * @Created 14.03.2024
 */

public record CustomerResponse(Long id, String firstName, String lastName, String identityNo, String email) implements UseCase {
}
