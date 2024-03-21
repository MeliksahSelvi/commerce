package com.commerce.order.service.order.handler.common;

import com.commerce.order.service.common.valueobject.Money;
import com.commerce.order.service.common.valueobject.Quantity;
import com.commerce.order.service.order.entity.Address;
import com.commerce.order.service.order.entity.OrderItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

public class CreateOrderCommonData {

    public Money correctCost() {
        return buildOrderItemsCorrect().stream()
                .map(OrderItem::getTotalPrice)
                .reduce(new Money(BigDecimal.ZERO), Money::add);
    }

    public Money wrongCost() {
        return correctCost().substract(new Money(BigDecimal.ONE));
    }

    public Money costNotGreaterThanZero() {
        return new Money(BigDecimal.ZERO);
    }

    public List<OrderItem> buildOrderItemsCorrect() {
        return List.of(OrderItem.builder()
                        .productId(1L)
                        .totalPrice(new Money(BigDecimal.valueOf(10.00)))
                        .price(new Money(BigDecimal.valueOf(1.00)))
                        .quantity(new Quantity(10))
                        .build(),
                OrderItem.builder()
                        .productId(2L)
                        .totalPrice(new Money(BigDecimal.valueOf(1.00)))
                        .price(new Money(BigDecimal.valueOf(1.00)))
                        .quantity(new Quantity(1))
                        .build(),
                OrderItem.builder()
                        .productId(3L)
                        .totalPrice(new Money(BigDecimal.valueOf(6.00)))
                        .price(new Money(BigDecimal.valueOf(2.00)))
                        .quantity(new Quantity(3))
                        .build());
    }

    public List<OrderItem> buildOrderItemsWrongWithPriceNotGreaterThanZero() {
        return List.of(OrderItem.builder()
                        .productId(1L)
                        .totalPrice(new Money(BigDecimal.valueOf(10.00)))
                        .price(new Money(BigDecimal.valueOf(0.00)))
                        .quantity(new Quantity(10))
                        .build(),
                OrderItem.builder()
                        .productId(2L)
                        .totalPrice(new Money(BigDecimal.valueOf(1.00)))
                        .price(new Money(BigDecimal.valueOf(1.00)))
                        .quantity(new Quantity(1))
                        .build(),
                OrderItem.builder()
                        .productId(3L)
                        .totalPrice(new Money(BigDecimal.valueOf(6.00)))
                        .price(new Money(BigDecimal.valueOf(2.00)))
                        .quantity(new Quantity(3))
                        .build());
    }

    public List<OrderItem> buildOrderItemsWrongWithTotalPriceNotEqualToMultiplyPrice() {
        return List.of(OrderItem.builder()
                        .productId(1L)
                        .totalPrice(new Money(BigDecimal.valueOf(9.00)))
                        .price(new Money(BigDecimal.valueOf(1.00)))
                        .quantity(new Quantity(10))
                        .build(),
                OrderItem.builder()
                        .productId(2L)
                        .totalPrice(new Money(BigDecimal.valueOf(1.00)))
                        .price(new Money(BigDecimal.valueOf(1.00)))
                        .quantity(new Quantity(1))
                        .build(),
                OrderItem.builder()
                        .productId(3L)
                        .totalPrice(new Money(BigDecimal.valueOf(6.00)))
                        .price(new Money(BigDecimal.valueOf(2.00)))
                        .quantity(new Quantity(3))
                        .build());
    }

    public Address buildAddress() {
        return Address.builder()
                .id(1L)
                .city("city")
                .county("county")
                .neighborhood("neigborhood")
                .street("street")
                .postalCode("postalCode")
                .build();
    }

    public boolean addressEqualToAnother(Address address, Address another) {
        if (address == null && another == null) {
            return true;
        }
        if (address == null || another == null) {
            return false;
        }

        return Objects.equals(address.getCity(), another.getCity()) &&
                Objects.equals(address.getCounty(), another.getCounty()) &&
                Objects.equals(address.getNeighborhood(), another.getNeighborhood()) &&
                Objects.equals(address.getStreet(), another.getStreet()) &&
                Objects.equals(address.getPostalCode(), another.getPostalCode());
    }

    public boolean orderItemsEqualToAnother(List<OrderItem> items, List<OrderItem> anotherItems) {
        if (items == null && anotherItems == null) {
            return true;
        }
        if (items == null || anotherItems == null) {
            return false;
        }
        if (items.size() != anotherItems.size()) {
            return false;
        }

        for (int i = 0; i < items.size(); i++) {
            OrderItem item = items.get(i);
            OrderItem anotherItem = anotherItems.get(i);

            if (!Objects.equals(item.getOrderId(), anotherItem.getOrderId()) ||
                    !Objects.equals(item.getProductId(), anotherItem.getProductId()) ||
                    !Objects.equals(item.getQuantity(), anotherItem.getQuantity()) ||
                    !Objects.equals(item.getPrice(), anotherItem.getPrice()) ||
                    !Objects.equals(item.getTotalPrice(), anotherItem.getTotalPrice())) {
                return false;
            }
        }

        return true;
    }
}
