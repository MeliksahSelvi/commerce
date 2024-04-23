package com.commerce.user.service.user.usecase;

import com.commerce.user.service.common.model.UseCase;

/**
 * @Author mselvi
 * @Created 21.04.2024
 */

public record UserRetrieve(Long userId) implements UseCase {
}
