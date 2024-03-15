package com.commerce.inventory.service.inventory.usecase;

import com.commerce.inventory.service.common.model.UseCase;

/**
 * @Author mselvi
 * @Created 12.03.2024
 */

public record ProductRetrieve(Long productId) implements UseCase {
}
