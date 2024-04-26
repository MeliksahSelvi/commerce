package com.commerce.payment.service.customer.usecase;

import com.commerce.payment.service.common.model.UseCase;

/**
 * @Author mselvi
 * @Created 24.04.2024
 */

public record CustomerSave(Long customerId,String firstName, String lastName, String identityNo, String email, String password) implements UseCase {
}
