package com.commerce.payment.service.adapters.outbox.jpa.repository;

import com.commerce.payment.service.adapters.outbox.jpa.entity.OrderOutboxEntity;
import com.commerce.payment.service.common.outbox.OutboxStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

@Repository
public interface OrderOutboxEntityRepository extends JpaRepository<OrderOutboxEntity, Long> {

    Optional<List<OrderOutboxEntity>> findByOutboxStatus(OutboxStatus outboxStatus);

    void deleteByOutboxStatus(OutboxStatus outboxStatus);
}
