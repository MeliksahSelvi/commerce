package com.commerce.order.service.order.handler.adapter;

import com.commerce.order.service.common.valueobject.Money;
import com.commerce.order.service.common.valueobject.OrderStatus;
import com.commerce.order.service.common.valueobject.Quantity;
import com.commerce.order.service.order.entity.Address;
import com.commerce.order.service.order.entity.Order;
import com.commerce.order.service.order.entity.OrderItem;
import com.commerce.order.service.order.port.jpa.OrderDataPort;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

public class FakeOrderDataAdapter implements OrderDataPort {

    private static final Long EXIST_ORDER_ID = 1L;

    @Override
    public Order save(Order order) {
        return Order.builder()
                .id(order.getId())
                .cost(order.getCost())
                .failureMessages(order.getFailureMessages())
                .items(order.getItems())
                .deliveryAddress(order.getDeliveryAddress())
                .orderStatus(order.getOrderStatus())
                .customerId(order.getCustomerId())
                .build();
    }

    @Override
    public Optional<Order> findById(Long orderId) {
        if (EXIST_ORDER_ID != orderId) {
            return Optional.empty();
        }
        return Optional.of(
                Order.builder()
                        .id(EXIST_ORDER_ID)
                        .cost(new Money(BigDecimal.TEN))
                        .failureMessages(Collections.EMPTY_LIST)
                        .items(List.of(
                                OrderItem.builder()
                                        .id(1L)
                                        .orderId(EXIST_ORDER_ID)
                                        .productId(1L)
                                        .totalPrice(new Money(BigDecimal.TEN))
                                        .price(new Money(BigDecimal.ONE))
                                        .quantity(new Quantity(10))
                                        .build(),
                                OrderItem.builder()
                                        .id(2L)
                                        .orderId(EXIST_ORDER_ID)
                                        .productId(2L)
                                        .totalPrice(new Money(BigDecimal.ONE))
                                        .price(new Money(BigDecimal.ONE))
                                        .quantity(new Quantity(1))
                                        .build(),
                                OrderItem.builder()
                                        .id(3L)
                                        .orderId(EXIST_ORDER_ID)
                                        .productId(3L)
                                        .totalPrice(new Money(BigDecimal.valueOf(6)))
                                        .price(new Money(BigDecimal.valueOf(2)))
                                        .quantity(new Quantity(3))
                                        .build()
                        ))
                        .deliveryAddress(
                                Address.builder()
                                        .id(1L)
                                        .city("city")
                                        .county("county")
                                        .neighborhood("neigborhood")
                                        .street("street")
                                        .postalCode("postalCode")
                                        .build()
                        )
                        .orderStatus(OrderStatus.PAID)
                        .customerId(1L)
                        .build()
        );
    }
}
