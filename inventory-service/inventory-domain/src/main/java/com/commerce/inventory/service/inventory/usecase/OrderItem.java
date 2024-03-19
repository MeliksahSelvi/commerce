package com.commerce.inventory.service.inventory.usecase;

import com.commerce.inventory.service.common.model.UseCase;
import com.commerce.inventory.service.common.valueobject.Money;
import com.commerce.inventory.service.common.valueobject.Quantity;

/**
 * @Author mselvi
 * @Created 09.03.2024
 */

public record OrderItem(Long id, Long orderId, Long productId, Quantity quantity, Money price, Money totalPrice) implements UseCase {
}
