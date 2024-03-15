package com.commerce.order.service.adapters.outbox.jpa.repository;

import com.commerce.order.service.adapters.outbox.jpa.entity.InventoryOutboxEntity;
import com.commerce.order.service.common.outbox.OutboxStatus;
import com.commerce.order.service.common.saga.SagaStatus;
import com.commerce.order.service.common.valueobject.OrderInventoryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @Author mselvi
 * @Created 06.03.2024
 */

@Repository
public interface InventoryOutboxEntityRepository extends JpaRepository<InventoryOutboxEntity, Long> {

    Optional<List<InventoryOutboxEntity>> findByOutboxStatusAndSagaStatusIn(OutboxStatus outboxStatus, List<SagaStatus> sagaStatuses);

    Optional<List<InventoryOutboxEntity>> findByOutboxStatusAndSagaStatusAndOrderInventoryStatusIn(
            OutboxStatus outboxStatus, SagaStatus sagaStatus, List<OrderInventoryStatus> orderInventoryStatuses);

    Optional<InventoryOutboxEntity> findBySagaIdAndSagaStatusIn(UUID sagaId, List<SagaStatus> sagaStatuses);

    void deleteByOutboxStatusAndSagaStatusIn(OutboxStatus outboxStatus, List<SagaStatus> sagaStatuses);

    void deleteByOutboxStatusAndSagaStatusAndOrderInventoryStatusIn(
            OutboxStatus outboxStatus, SagaStatus sagaStatus, List<OrderInventoryStatus> orderInventoryStatuses);
}
