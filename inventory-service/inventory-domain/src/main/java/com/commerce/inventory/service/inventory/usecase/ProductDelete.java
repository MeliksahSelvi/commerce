package com.commerce.inventory.service.inventory.usecase;

import com.commerce.inventory.service.common.model.UseCase;

/**
 * @Author mselvi
 * @Created 15.04.2024
 */

public record ProductDelete(Long productId) implements UseCase {
}
