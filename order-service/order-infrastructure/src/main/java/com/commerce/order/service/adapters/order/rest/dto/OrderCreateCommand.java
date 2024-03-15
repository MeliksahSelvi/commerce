package com.commerce.order.service.adapters.order.rest.dto;

import com.commerce.order.service.common.valueobject.Money;
import com.commerce.order.service.order.usecase.CreateOrder;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author mselvi
 * @Created 05.03.2024
 */

public record OrderCreateCommand(@NotNull Long customerId, @Positive BigDecimal cost,
                                 @Valid List<OrderItemDto> items, @Valid AddressDto deliveryAddress) {

    public CreateOrder toUseCase() {
        return new CreateOrder(customerId, new Money(cost), items.stream().map(OrderItemDto::toModel).toList(), deliveryAddress.toModel());
    }
}
