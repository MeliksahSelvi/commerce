package com.commerce.user.service.user.usecase;

import com.commerce.user.service.common.model.UseCase;

/**
 * @Author mselvi
 * @Created 15.04.2024
 */

public record UserDelete(Long userId) implements UseCase {
}
