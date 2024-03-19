package com.commerce.order.service.adapters.order.jpa.entity;

import com.commerce.order.service.common.model.AbstractEntity;
import com.commerce.order.service.common.valueobject.Money;
import com.commerce.order.service.common.valueobject.OrderStatus;
import com.commerce.order.service.order.entity.Order;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Author mselvi
 * @Created 05.03.2024
 */

@Entity
@Table(name = "ORDERS")
public class OrderEntity extends AbstractEntity {

    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private AddressEntity address;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<OrderItemEntity> items;

    @Column(name = "COST", precision = 15, scale = 2)
    private BigDecimal cost;

    @Column(name = "ORDER_STATUS")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "FAILURE_MESSAGES", length = 4000)
    private String failureMessages;

    public Order toModel() {
        return Order.builder()
                .id(getId())
                .customerId(customerId)
                .orderStatus(orderStatus)
                .cost(new Money(cost))
                .deliveryAddress(address.toModel())
                .items(items.stream().map(OrderItemEntity::toModel).toList())
                .failureMessages(failureMessages.isEmpty() ? Collections.EMPTY_LIST : Arrays.asList(failureMessages.split(",")))
                .build();
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public List<OrderItemEntity> getItems() {
        return items;
    }

    public void setItems(List<OrderItemEntity> items) {
        this.items = items;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getFailureMessages() {
        return failureMessages;
    }

    public void setFailureMessages(String failureMessages) {
        this.failureMessages = failureMessages;
    }
}
