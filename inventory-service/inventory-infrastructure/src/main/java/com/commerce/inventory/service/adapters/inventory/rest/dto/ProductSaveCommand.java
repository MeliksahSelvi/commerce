package com.commerce.inventory.service.adapters.inventory.rest.dto;

import com.commerce.inventory.service.common.valueobject.Availability;
import com.commerce.inventory.service.common.valueobject.Money;
import com.commerce.inventory.service.common.valueobject.Quantity;
import com.commerce.inventory.service.inventory.usecase.ProductSave;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

/**
 * @Author mselvi
 * @Created 12.03.2024
 */

public record ProductSaveCommand(@NotEmpty String name, @NotNull @Positive BigDecimal price,
                                 @NotNull @Positive Integer quantity, @NotNull Boolean availability) {

    public ProductSave toModel() {
        return new ProductSave(name, new Money(price), new Quantity(quantity), new Availability(availability));
    }
}
