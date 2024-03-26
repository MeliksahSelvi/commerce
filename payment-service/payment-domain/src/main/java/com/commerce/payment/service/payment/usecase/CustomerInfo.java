package com.commerce.payment.service.payment.usecase;

import com.commerce.payment.service.common.model.UseCase;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

public record CustomerInfo(Long id, String firstName, String lastName, String identityNo, String email) implements UseCase {
}
