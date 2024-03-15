package com.commerce.order.service.adapters.outbox.jpa.repository;

import com.commerce.order.service.adapters.outbox.jpa.entity.PaymentOutboxEntity;
import com.commerce.order.service.common.outbox.OutboxStatus;
import com.commerce.order.service.common.saga.SagaStatus;
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
public interface PaymentOutboxEntityRepository extends JpaRepository<PaymentOutboxEntity, Long> {

    Optional<List<PaymentOutboxEntity>> findByOutboxStatusAndSagaStatusIn(OutboxStatus outboxStatus, List<SagaStatus> sagaStatuses);

    Optional<PaymentOutboxEntity> findBySagaIdAndSagaStatusIn(UUID sagaId,List<SagaStatus> sagaStatuses);

    void deleteByOutboxStatusAndSagaStatusIn(OutboxStatus outboxStatus,List<SagaStatus> sagaStatuses);
}
