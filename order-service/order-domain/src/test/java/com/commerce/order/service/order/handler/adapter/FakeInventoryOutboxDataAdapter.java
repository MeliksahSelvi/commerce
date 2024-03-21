package com.commerce.order.service.order.handler.adapter;

import com.commerce.order.service.common.outbox.OutboxStatus;
import com.commerce.order.service.common.saga.SagaStatus;
import com.commerce.order.service.common.valueobject.OrderInventoryStatus;
import com.commerce.order.service.common.valueobject.OrderStatus;
import com.commerce.order.service.outbox.entity.InventoryOutbox;
import com.commerce.order.service.outbox.port.jpa.InventoryOutboxDataPort;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

public class FakeInventoryOutboxDataAdapter implements InventoryOutboxDataPort {

    private boolean deleteActionForOutboxAndSagaStatuses = false;
    private boolean deleteActionForOutboxAndSagaStatusAndOrderInventoryStatus = false;

    @Override
    public InventoryOutbox save(InventoryOutbox inventoryOutbox) {
        deleteActionForOutboxAndSagaStatuses = false;
        deleteActionForOutboxAndSagaStatusAndOrderInventoryStatus=false;
        return InventoryOutbox.builder()
                .id(inventoryOutbox.getId())
                .orderStatus(inventoryOutbox.getOrderStatus())
                .orderInventoryStatus(inventoryOutbox.getOrderInventoryStatus())
                .outboxStatus(inventoryOutbox.getOutboxStatus())
                .payload(inventoryOutbox.getPayload())
                .sagaId(inventoryOutbox.getSagaId())
                .sagaStatus(inventoryOutbox.getSagaStatus())
                .build();
    }

    @Override
    public Optional<List<InventoryOutbox>> findByOutboxStatusAndSagaStatuses(OutboxStatus outboxStatus, SagaStatus... sagaStatuses) {
        if (deleteActionForOutboxAndSagaStatuses) {
            return Optional.of(Collections.EMPTY_LIST);
        }
        return Optional.of(List.of(
                InventoryOutbox.builder()
                        .id(1L)
                        .orderStatus(OrderStatus.CHECKING)
                        .orderInventoryStatus(OrderInventoryStatus.CHECKING)
                        .outboxStatus(outboxStatus)
                        .payload("payload")
                        .sagaId(UUID.randomUUID())
                        .sagaStatus(sagaStatuses[0])
                        .build(),
                InventoryOutbox.builder()
                        .id(2L)
                        .orderStatus(OrderStatus.PENDING)
                        .orderInventoryStatus(OrderInventoryStatus.CHECKING)
                        .outboxStatus(outboxStatus)
                        .payload("payload2")
                        .sagaId(UUID.randomUUID())
                        .sagaStatus(sagaStatuses[1])
                        .build(),
                InventoryOutbox.builder()
                        .id(3L)
                        .orderStatus(OrderStatus.PAID)
                        .orderInventoryStatus(OrderInventoryStatus.UPDATING)
                        .outboxStatus(outboxStatus)
                        .payload("payload3")
                        .sagaId(UUID.randomUUID())
                        .sagaStatus(sagaStatuses[2])
                        .build()
        ));
    }

    @Override
    public Optional<List<InventoryOutbox>> findByOutboxStatusAndSagaStatusAndOrderInventoryStatus(OutboxStatus outboxStatus, SagaStatus sagaStatus, OrderInventoryStatus orderInventoryStatus) {
        if (deleteActionForOutboxAndSagaStatusAndOrderInventoryStatus) {
            return Optional.of(Collections.EMPTY_LIST);
        }
        return Optional.of(List.of(
                InventoryOutbox.builder()
                        .id(1L)
                        .orderStatus(OrderStatus.CHECKING)
                        .orderInventoryStatus(orderInventoryStatus)
                        .outboxStatus(outboxStatus)
                        .payload("payload")
                        .sagaId(UUID.randomUUID())
                        .sagaStatus(sagaStatus)
                        .build(),
                InventoryOutbox.builder()
                        .id(2L)
                        .orderStatus(OrderStatus.PENDING)
                        .orderInventoryStatus(orderInventoryStatus)
                        .outboxStatus(outboxStatus)
                        .payload("payload2")
                        .sagaId(UUID.randomUUID())
                        .sagaStatus(sagaStatus)
                        .build(),
                InventoryOutbox.builder()
                        .id(3L)
                        .orderStatus(OrderStatus.PAID)
                        .orderInventoryStatus(orderInventoryStatus)
                        .outboxStatus(outboxStatus)
                        .payload("payload3")
                        .sagaId(UUID.randomUUID())
                        .sagaStatus(sagaStatus)
                        .build()
        ));
    }

    @Override
    public Optional<InventoryOutbox> findBySagaIdAndSagaStatusAndOrderInventoryStatus(UUID sagaId, SagaStatus sagaStatus, OrderInventoryStatus orderInventoryStatus) {
        return Optional.of(
                InventoryOutbox.builder()
                        .id(1L)
                        .orderStatus(OrderStatus.CHECKING)
                        .orderInventoryStatus(orderInventoryStatus)
                        .outboxStatus(OutboxStatus.STARTED)
                        .payload("payload")
                        .sagaId(sagaId)
                        .sagaStatus(sagaStatus)
                        .build());
    }

    @Override
    public Optional<InventoryOutbox> findBySagaIdAndSagaStatuses(UUID sagaId, SagaStatus... sagaStatuses) {
        return Optional.of(
                InventoryOutbox.builder()
                        .id(1L)
                        .orderStatus(OrderStatus.CHECKING)
                        .orderInventoryStatus(OrderInventoryStatus.UPDATING)
                        .outboxStatus(OutboxStatus.STARTED)
                        .payload("payload")
                        .sagaId(sagaId)
                        .sagaStatus(sagaStatuses[0])
                        .build());
    }

    @Override
    public void deleteByOutboxStatusAndSagaStatuses(OutboxStatus outboxStatus, SagaStatus... sagaStatuses) {
        deleteActionForOutboxAndSagaStatuses = true;
    }

    @Override
    public void deleteByOutboxStatusAndSagaStatusAndOrderInventoryStatus(OutboxStatus outboxStatus, SagaStatus sagaStatus, OrderInventoryStatus orderInventoryStatus) {
        deleteActionForOutboxAndSagaStatusAndOrderInventoryStatus =true;
    }
}
