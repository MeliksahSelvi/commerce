package com.commerce.inventory.service.inventory.usecase;

import com.commerce.inventory.service.common.model.UseCase;
import com.commerce.inventory.service.common.valueobject.Availability;
import com.commerce.inventory.service.common.valueobject.Money;
import com.commerce.inventory.service.common.valueobject.Quantity;

/**
 * @Author mselvi
 * @Created 12.03.2024
 */

public record ProductSave(String name, Money price, Quantity quantity, Availability availability) implements UseCase {
}
