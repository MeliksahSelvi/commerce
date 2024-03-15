package com.commerce.order.service.order.entity;

import com.commerce.order.service.common.exception.OrderDomainException;
import com.commerce.order.service.common.model.BaseEntity;
import com.commerce.order.service.common.valueobject.Address;
import com.commerce.order.service.common.valueobject.Money;
import com.commerce.order.service.common.valueobject.OrderStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author mselvi
 * @Created 01.03.2024
 */

public class Order extends BaseEntity {

    private final Long customerId;
    private final Address deliveryAddress;
    private final Money cost;
    private final List<OrderItem> items;

    private OrderStatus orderStatus;
    private List<String> failureMessages;

    public void check() {
        if (orderStatus != OrderStatus.CHECKING) {
            throw new OrderDomainException("Order is not correct state for mark operation!");
        }
        orderStatus = OrderStatus.PENDING;
    }

    public void pay() {
        if (orderStatus != OrderStatus.PENDING) {
            throw new OrderDomainException("Order is not correct state for pay operation!");
        }
        orderStatus = OrderStatus.PAID;
    }

    public void approve() {
        if (orderStatus != OrderStatus.PAID) {
            throw new OrderDomainException("Order is not correct state for approve operation!");
        }
        orderStatus = OrderStatus.APPROVED;
    }

    public void initCancel(List<String> failureMessages) {
        orderStatus = OrderStatus.CANCELLING;
        updateFailureMessages(failureMessages);
    }

    public void cancel(List<String> failureMessages) {
        if (!(orderStatus == OrderStatus.CHECKING || orderStatus == OrderStatus.PENDING || orderStatus == OrderStatus.CANCELLING)) {
            throw new OrderDomainException("Order is not correct state for cancel operation!");
        }
        orderStatus = OrderStatus.CANCELLED;
        updateFailureMessages(failureMessages);
    }

    private void updateFailureMessages(List<String> failureMessages) {
        if (failureMessages.isEmpty()) {
            return;
        }
        if (this.failureMessages == null) {
            this.failureMessages = new ArrayList<>();
        }
        this.failureMessages.addAll(failureMessages);
    }

    public void initializeOrder() {
        orderStatus = OrderStatus.CHECKING;
        associateOrderToOrderItems();
    }

    private void associateOrderToOrderItems() {
        for (OrderItem orderItem : items) {
            orderItem.initializeOrderItem(getId());
        }
    }

    public void validateOrder() {
        validateOrderInitialState();
        validateCost();
        validateItemsPrice();
    }

    private void validateOrderInitialState() {
        if (orderStatus != null) {
            throw new OrderDomainException("Order is not in correct state for initialization!");
        }
    }

    private void validateCost() {
        if (cost == null || !cost.isGreaterThanZero()) {
            throw new OrderDomainException("Cost must be greater than zero!");
        }
    }

    private void validateItemsPrice() {
        Money wholeTotalPrice = items.stream().map(orderItem -> {
            validateItemPrice(orderItem);
            return orderItem.getTotalPrice();
        }).reduce(new Money(BigDecimal.ZERO), Money::add);

        if (!cost.equals(wholeTotalPrice)) {
            throw new OrderDomainException(String.format("Total price: %.2f is not equal Order Items total: %.2f!", cost.amount(), wholeTotalPrice.amount()));
        }
    }

    private void validateItemPrice(OrderItem orderItem) {
        if (!orderItem.isTotalPriceValid()) {
            throw new OrderDomainException(String.format("Order item price: %.2f is not valid for product %d", orderItem.getPrice().amount(), orderItem.getProductId()));
        }
    }


    public static Builder builder() {
        return new Builder();
    }

    private Order(Builder builder) {
        setId(builder.id);
        this.customerId = builder.customerId;
        this.deliveryAddress = builder.deliveryAddress;
        this.cost = builder.cost;
        this.items = builder.items;
        this.orderStatus = builder.orderStatus;
        this.failureMessages = builder.failureMessages;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public Money getCost() {
        return cost;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public List<String> getFailureMessages() {
        return failureMessages;
    }

    public static final class Builder {
        private Long id;
        private Long customerId;
        private Address deliveryAddress;
        private Money cost;
        private List<OrderItem> items;
        private OrderStatus orderStatus;
        private List<String> failureMessages;

        private Builder() {
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder customerId(Long val) {
            customerId = val;
            return this;
        }

        public Builder deliveryAddress(Address val) {
            deliveryAddress = val;
            return this;
        }

        public Builder cost(Money val) {
            cost = val;
            return this;
        }

        public Builder items(List<OrderItem> val) {
            items = val;
            return this;
        }

        public Builder orderStatus(OrderStatus val) {
            orderStatus = val;
            return this;
        }

        public Builder failureMessages(List<String> val) {
            failureMessages = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }

}
