package com.commerce.inventory.service.adapters.outbox.jpa.repository;

import com.commerce.inventory.service.adapters.outbox.jpa.entity.OrderOutboxEntity;
import com.commerce.inventory.service.common.outbox.OutboxStatus;
import com.commerce.inventory.service.common.valueobject.OrderInventoryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

@Repository
public interface OrderOutboxEntityRepository extends JpaRepository<OrderOutboxEntity, Long> {

    Optional<List<OrderOutboxEntity>> findByOutboxStatus(OutboxStatus outboxStatus);

    Optional<OrderOutboxEntity> findBySagaIdAndOrderInventoryStatus(UUID sagaId, OrderInventoryStatus orderInventoryStatus);

    void deleteByOutboxStatus(OutboxStatus outboxStatus);
}
