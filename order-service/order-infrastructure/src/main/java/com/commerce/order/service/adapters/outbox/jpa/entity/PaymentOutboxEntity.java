package com.commerce.order.service.adapters.outbox.jpa.entity;

import com.commerce.order.service.common.model.AbstractEntity;
import com.commerce.order.service.common.outbox.OutboxStatus;
import com.commerce.order.service.common.saga.SagaStatus;
import com.commerce.order.service.common.valueobject.OrderStatus;
import com.commerce.order.service.outbox.entity.PaymentOutbox;
import jakarta.persistence.*;

import java.util.UUID;

/**
 * @Author mselvi
 * @Created 06.03.2024
 */

@Entity
@Table(name = "PAYMENT_OUTBOX")
public class PaymentOutboxEntity extends AbstractEntity {

    @Column(name = "SAGA_ID",nullable = false)
    private UUID sagaId;

    @Column(name = "PAYLOAD", length = 4000,nullable = false)
    private String payload;

    @Column(name = "ORDER_STATUS",nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "SAGA_STATUS",nullable = false)
    @Enumerated(EnumType.STRING)
    private SagaStatus sagaStatus;

    @Column(name = "OUTBOX_STATUS",nullable = false)
    @Enumerated(EnumType.STRING)
    private OutboxStatus outboxStatus;

    public PaymentOutbox toModel() {
        return PaymentOutbox.builder()
                .id(getId())
                .sagaId(sagaId)
                .payload(payload)
                .orderStatus(orderStatus)
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
