package com.commerce.inventory.service.adapters.outbox.jpa.entity;

import com.commerce.inventory.service.common.model.AbstractEntity;
import com.commerce.inventory.service.common.outbox.OutboxStatus;
import com.commerce.inventory.service.common.valueobject.InventoryStatus;
import com.commerce.inventory.service.common.valueobject.OrderInventoryStatus;
import com.commerce.inventory.service.outbox.entity.OrderOutbox;
import jakarta.persistence.*;

import java.util.UUID;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

@Entity
@Table(name = "ORDER_OUTBOX")
public class OrderOutboxEntity extends AbstractEntity {

    @Column(name = "SAGA_ID",nullable = false)
    private UUID sagaId;

    @Column(name = "PAYLOAD", length = 4000,nullable = false)
    private String payload;

    @Column(name = "OUTBOX_STATUS",nullable = false)
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
