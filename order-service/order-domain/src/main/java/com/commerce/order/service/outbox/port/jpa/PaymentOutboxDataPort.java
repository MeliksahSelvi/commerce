package com.commerce.order.service.outbox.port.jpa;

import com.commerce.order.service.common.outbox.OutboxStatus;
import com.commerce.order.service.common.saga.SagaStatus;
import com.commerce.order.service.outbox.model.PaymentOutbox;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @Author mselvi
 * @Created 02.03.2024
 */

public interface PaymentOutboxDataPort {

    PaymentOutbox save(PaymentOutbox paymentOutbox);

    Optional<List<PaymentOutbox>> findByOutboxStatusAndSagaStatuses(OutboxStatus outboxStatus, SagaStatus... sagaStatuses);

    Optional<PaymentOutbox> findBySagaIdAndSagaStatuses(UUID sagaId, SagaStatus... sagaStatuses);

    void deleteByOutboxStatusAndSagaStatuses(OutboxStatus outboxStatus, SagaStatus... sagaStatuses);
}
