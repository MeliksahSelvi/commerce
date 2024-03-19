package com.commerce.order.service.order.usecase;

import com.commerce.order.service.common.model.UseCase;
import com.commerce.order.service.order.entity.Address;
import com.commerce.order.service.common.valueobject.Money;
import com.commerce.order.service.order.entity.Order;
import com.commerce.order.service.order.entity.OrderItem;

import java.util.List;

/**
 * @Author mselvi
 * @Created 05.03.2024
 */

public record CreateOrder(Long customerId, Money cost, List<OrderItem> items, Address deliveryAddress) implements UseCase {

    public Order toModel() {
        return Order.builder()
                .customerId(customerId)
                .cost(cost)
                .items(items)
                .deliveryAddress(deliveryAddress)
                .build();
    }
}
