package com.commerce.order.service.outbox.port.jpa;

import com.commerce.order.service.common.outbox.OutboxStatus;
import com.commerce.order.service.common.saga.SagaStatus;
import com.commerce.order.service.common.valueobject.OrderInventoryStatus;
import com.commerce.order.service.outbox.entity.InventoryOutbox;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @Author mselvi
 * @Created 04.03.2024
 */

public interface InventoryOutboxDataPort {

    InventoryOutbox save(InventoryOutbox inventoryOutbox);

    Optional<List<InventoryOutbox>> findByOutboxStatusAndSagaStatuses(OutboxStatus outboxStatus, SagaStatus... sagaStatuses);

    Optional<List<InventoryOutbox>> findByOutboxStatusAndSagaStatusAndOrderInventoryStatus(
            OutboxStatus outboxStatus, SagaStatus sagaStatus, OrderInventoryStatus orderInventoryStatus);

    Optional<InventoryOutbox> findBySagaIdAndSagaStatusAndOrderInventoryStatus(UUID sagaId, SagaStatus sagaStatus, OrderInventoryStatus orderInventoryStatus);

    Optional<InventoryOutbox> findBySagaIdAndSagaStatuses(UUID sagaId, SagaStatus... sagaStatuses);

    void deleteByOutboxStatusAndSagaStatuses(OutboxStatus outboxStatus, SagaStatus... sagaStatuses);

    void deleteByOutboxStatusAndSagaStatusAndOrderInventoryStatus(OutboxStatus outboxStatus, SagaStatus sagaStatus, OrderInventoryStatus orderInventoryStatus);
}
