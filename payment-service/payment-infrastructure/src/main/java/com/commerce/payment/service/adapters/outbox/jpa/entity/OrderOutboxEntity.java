package com.commerce.payment.service.adapters.outbox.jpa.entity;

import com.commerce.payment.service.common.model.AbstractEntity;
import com.commerce.payment.service.common.outbox.OutboxStatus;
import com.commerce.payment.service.outbox.entity.OrderOutbox;
import jakarta.persistence.*;

import java.util.UUID;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

@Entity
@Table(name = "ORDER_OUTBOX")
public class OrderOutboxEntity extends AbstractEntity {

    @Column(name = "SAGA_ID")
    private UUID sagaId;

    @Column(name = "PAYLOAD", length = 4000)
    private String payload;

    @Column(name = "OUTBOX_STATUS")
    @Enumerated(EnumType.STRING)
    private OutboxStatus outboxStatus;


    public OrderOutbox toModel() {
        return OrderOutbox.builder()
                .id(getId())
                .sagaId(sagaId)
                .payload(payload)
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

    public void setOutboxStatus(OutboxStatus outboxStatus) {
        this.outboxStatus = outboxStatus;
    }

}
