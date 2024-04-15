package com.commerce.customer.service.customer.usecase;

import com.commerce.customer.service.common.model.UseCase;

/**
 * @Author mselvi
 * @Created 10.03.2024
 */

public record CustomerSave(Long customerId,String firstName, String lastName, String identityNo, String email, String password) implements UseCase {
}
