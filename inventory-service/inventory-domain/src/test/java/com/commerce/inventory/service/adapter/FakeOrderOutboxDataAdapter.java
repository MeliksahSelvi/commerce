package com.commerce.inventory.service.adapter;

import com.commerce.inventory.service.common.outbox.OutboxStatus;
import com.commerce.inventory.service.outbox.model.OrderOutbox;
import com.commerce.inventory.service.outbox.port.jpa.OrderOutboxDataPort;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @Author mselvi
 * @Created 26.03.2024
 */

public class FakeOrderOutboxDataAdapter implements OrderOutboxDataPort {
    private boolean deleteActionForOutboxStatus = false;

    @Override
    public OrderOutbox save(OrderOutbox orderOutbox) {
        deleteActionForOutboxStatus = false;
        return OrderOutbox.builder()
                .id(orderOutbox.getId())
                .sagaId(orderOutbox.getSagaId())
                .outboxStatus(orderOutbox.getOutboxStatus())
                .payload(orderOutbox.getPayload())
                .build();
    }

    @Override
    public Optional<List<OrderOutbox>> findByOutboxStatus(OutboxStatus outboxStatus) {
        if (deleteActionForOutboxStatus) {
            return Optional.empty();
        }
        return Optional.of(List.of(
                OrderOutbox.builder()
                        .id(1L)
                        .sagaId(UUID.randomUUID())
                        .outboxStatus(outboxStatus)
                        .payload("payload")
                        .build(),
                OrderOutbox.builder()
                        .id(2L)
                        .sagaId(UUID.randomUUID())
                        .outboxStatus(outboxStatus)
                        .payload("payload2")
                        .build(),
                OrderOutbox.builder()
                        .id(3L)
                        .sagaId(UUID.randomUUID())
                        .outboxStatus(outboxStatus)
                        .payload("payload3")
                        .build()
        ));
    }

    @Override
    public void deleteByOutboxStatus(OutboxStatus outboxStatus) {
        deleteActionForOutboxStatus = true;
    }
}
