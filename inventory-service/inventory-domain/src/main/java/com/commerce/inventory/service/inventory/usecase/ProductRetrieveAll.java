package com.commerce.inventory.service.inventory.usecase;

import com.commerce.inventory.service.common.model.UseCase;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 12.03.2024
 */

public record ProductRetrieveAll(Optional<Integer> page, Optional<Integer> size) implements UseCase {
}
