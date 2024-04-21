package com.commerce.shipping.service.shipping.adapters;

import com.commerce.shipping.service.common.valueobject.DeliveryStatus;
import com.commerce.shipping.service.common.valueobject.Money;
import com.commerce.shipping.service.common.valueobject.Quantity;
import com.commerce.shipping.service.shipping.entity.Address;
import com.commerce.shipping.service.shipping.entity.OrderItem;
import com.commerce.shipping.service.shipping.entity.Shipping;
import com.commerce.shipping.service.shipping.port.jpa.ShippingDataPort;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

public class FakeOrderNotificationShippingDataAdapter implements ShippingDataPort {

    private static final Long EXIST_ORDER_ID = 1L;
    private static final Long ALREADY_SENT_ORDER_ID=2L;

    @Override
    public Shipping save(Shipping shipping) {
        return Shipping.builder()
                .id(shipping.getId())
                .orderId(shipping.getOrderId())
                .customerId(shipping.getCustomerId())
                .items(shipping.getItems())
                .address(shipping.getAddress())
                .deliveryStatus(shipping.getDeliveryStatus())
                .build();
    }

    @Override
    public Optional<Shipping> findByOrderId(Long orderId) {
        if (EXIST_ORDER_ID != orderId) {
            return Optional.empty();
        }
        return Optional.of(
                Shipping.builder()
                        .id(1L)
                        .orderId(orderId)
                        .customerId(1L)
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
                        .address(
                                Address.builder()
                                        .id(1L)
                                        .city("city")
                                        .county("county")
                                        .neighborhood("neigborhood")
                                        .street("street")
                                        .postalCode("postalCode")
                                        .build()
                        )
                        .deliveryStatus(DeliveryStatus.APPROVED)
                        .build()
        );
    }

    @Override
    public Optional<Shipping> findByOrderIdAndDeliveryStatus(Long orderId, DeliveryStatus deliveryStatus) {
        if (ALREADY_SENT_ORDER_ID != orderId) {
            return Optional.empty();
        }
        return Optional.of(
                Shipping.builder()
                        .id(1L)
                        .orderId(orderId)
                        .customerId(1L)
                        .deliveryStatus(deliveryStatus)
                        .address(
                                Address.builder()
                                        .id(1L)
                                        .city("city")
                                        .county("county")
                                        .neighborhood("neigborhood")
                                        .street("street")
                                        .postalCode("postalCode")
                                        .build()
                        )
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
                        .build()
        );
    }
}
