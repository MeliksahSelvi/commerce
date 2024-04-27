package com.commerce.order.service.adapters.outbox.jpa.entity;

import com.commerce.order.service.common.model.BaseEntity;
import com.commerce.order.service.common.outbox.OutboxStatus;
import com.commerce.order.service.common.saga.SagaStatus;
import com.commerce.order.service.common.valueobject.OrderInventoryStatus;
import com.commerce.order.service.common.valueobject.OrderStatus;
import com.commerce.order.service.outbox.model.InventoryOutbox;
import jakarta.persistence.*;

import java.util.UUID;

/**
 * @Author mselvi
 * @Created 06.03.2024
 */

@Entity
@Table(name = "INVENTORY_OUTBOX")
public class InventoryOutboxEntity extends BaseEntity {

    @Column(name = "SAGA_ID",nullable = false)
    private UUID sagaId;

    @Column(name = "PAYLOAD", length = 4000,nullable = false)
    private String payload;

    @Column(name = "ORDER_INVENTORY_STATUS",nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderInventoryStatus orderInventoryStatus;

    @Column(name = "ORDER_STATUS",nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "SAGA_STATUS",nullable = false)
    @Enumerated(EnumType.STRING)
    private SagaStatus sagaStatus;

    @Column(name = "OUTBOX_STATUS",nullable = false)
    @Enumerated(EnumType.STRING)
    private OutboxStatus outboxStatus;

    public InventoryOutbox toModel() {
        return InventoryOutbox.builder()
                .id(getId())
                .sagaId(sagaId)
                .payload(payload)
                .orderStatus(orderStatus)
                .orderInventoryStatus(orderInventoryStatus)
                .sagaStatus(sagaStatus)
                .outboxStatus(outboxStatus)
                .build();
    }

    public UUID getSagaId() {
        return sagaId;
    }

    public void setSagaId(UUID sagaId) {
        this.sagaId = sagaId;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public OrderInventoryStatus getOrderInventoryStatus() {
        return orderInventoryStatus;
    }

    public void setOrderInventoryStatus(OrderInventoryStatus orderInventoryStatus) {
        this.orderInventoryStatus = orderInventoryStatus;
    }

    public SagaStatus getSagaStatus() {
        return sagaStatus;
    }

    public void setSagaStatus(SagaStatus sagaStatus) {
        this.sagaStatus = sagaStatus;
    }

    public OutboxStatus getOutboxStatus() {
        return outboxStatus;
    }

    public void setOutboxStatus(OutboxStatus outboxStatus) {
        this.outboxStatus = outboxStatus;
    }
}
