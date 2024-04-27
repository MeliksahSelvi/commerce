package com.commerce.payment.service.adapters.outbox.jpa.entity;

import com.commerce.payment.service.common.model.BaseEntity;
import com.commerce.payment.service.common.outbox.OutboxStatus;
import com.commerce.payment.service.common.valueobject.PaymentStatus;
import com.commerce.payment.service.outbox.model.OrderOutbox;
import jakarta.persistence.*;

import java.util.UUID;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

@Entity
@Table(name = "ORDER_OUTBOX")
public class OrderOutboxEntity extends BaseEntity {

    @Column(name = "SAGA_ID")
    private UUID sagaId;

    @Column(name = "PAYLOAD", length = 4000)
    private String payload;

    @Column(name = "OUTBOX_STATUS")
    @Enumerated(EnumType.STRING)
    private OutboxStatus outboxStatus;

    @Column(name = "PAYMENT_STATUS",nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    public OrderOutbox toModel() {
        return OrderOutbox.builder()
                .id(getId())
                .sagaId(sagaId)
                .payload(payload)
                .paymentStatus(paymentStatus)
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

    public OutboxStatus getOutboxStatus() {
        return outboxStatus;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void setOutboxStatus(OutboxStatus outboxStatus) {
        this.outboxStatus = outboxStatus;
    }

}
