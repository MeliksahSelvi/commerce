package com.commerce.order.service.adapters.order.rest.dto;

import com.commerce.order.service.common.valueobject.Money;
import com.commerce.order.service.common.valueobject.Quantity;
import com.commerce.order.service.order.entity.OrderItem;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

/**
 * @Author mselvi
 * @Created 05.03.2024
 */

public record OrderItemDto(@NotNull Long productId, @Positive int quantity,
                           @Positive BigDecimal price, @Positive BigDecimal totalPrice) {

    public OrderItemDto(OrderItem orderItem) {
        this(orderItem.getProductId(), orderItem.getQuantity().value(), orderItem.getPrice().amount(), orderItem.getTotalPrice().amount());
    }

    public OrderItem toModel() {
        return OrderItem.builder()
                .productId(productId)
                .quantity(new Quantity(quantity))
                .price(new Money(price))
                .totalPrice(new Money(totalPrice))
                .build();
    }
}
