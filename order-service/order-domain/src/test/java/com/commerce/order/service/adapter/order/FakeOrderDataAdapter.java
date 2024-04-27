package com.commerce.order.service.adapter.order;

import com.commerce.order.service.common.valueobject.Money;
import com.commerce.order.service.common.valueobject.OrderStatus;
import com.commerce.order.service.common.valueobject.Quantity;
import com.commerce.order.service.order.model.Address;
import com.commerce.order.service.order.model.Order;
import com.commerce.order.service.order.model.OrderItem;
import com.commerce.order.service.order.port.jpa.OrderDataPort;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 25.03.2024
 */

public class FakeOrderDataAdapter implements OrderDataPort {
    private static final Long NOT_EXIST_ORDER_ID = 7L;

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
        if (NOT_EXIST_ORDER_ID == orderId) {
            return Optional.empty();
        }
        return Optional.of(
                Order.builder()
                        .id(orderId)
                        .cost(new Money(BigDecimal.valueOf(17)))
                        .failureMessages(Collections.EMPTY_LIST)
                        .items(List.of(
                                OrderItem.builder()
                                        .id(1L)
                                        .orderId(orderId)
                                        .productId(1L)
                                        .totalPrice(new Money(BigDecimal.TEN))
                                        .price(new Money(BigDecimal.ONE))
                                        .quantity(new Quantity(10))
                                        .build(),
                                OrderItem.builder()
                                        .id(2L)
                                        .orderId(orderId)
                                        .productId(2L)
                                        .totalPrice(new Money(BigDecimal.ONE))
                                        .price(new Money(BigDecimal.ONE))
                                        .quantity(new Quantity(1))
                                        .build(),
                                OrderItem.builder()
                                        .id(3L)
                                        .orderId(orderId)
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
                        .orderStatus(chooseStatus(orderId))
                        .customerId(1L)
                        .build()
        );
    }

    private OrderStatus chooseStatus(Long orderId) {
        return OrderStatus.values()[orderId.intValue() - 1];
    }

//    private static final Long CHECKING_ORDER_ID = 1L;
//    private static final Long PENDING_ORDER_ID = 2L;
//    private static final Long PAID_ORDER_ID = 3L;
//    private static final Long APPROVED_ORDER_ID = 4L;
//    private static final Long CANCELLING_ORDER_ID = 5L;
//    private static final Long CANCELLED_ORDER_ID = 6L;
}
