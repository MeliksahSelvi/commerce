package com.commerce.order.service.order.usecase;

import com.commerce.order.service.common.model.UseCase;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

public record CustomerResponse(Long id, String firstName, String lastName, String identityNo, String email) implements UseCase {
}
