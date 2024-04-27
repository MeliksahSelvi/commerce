package com.commerce.order.service.adapter.outbox;

import com.commerce.order.service.common.outbox.OutboxStatus;
import com.commerce.order.service.common.saga.SagaStatus;
import com.commerce.order.service.common.valueobject.OrderStatus;
import com.commerce.order.service.outbox.model.PaymentOutbox;
import com.commerce.order.service.outbox.port.jpa.PaymentOutboxDataPort;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

public class FakePaymentOutboxDataAdapter implements PaymentOutboxDataPort {

    private static final UUID EXIST_SAGA_ID = UUID.fromString("5bf96862-0c98-41ef-a952-e03d2ded6a6a");
    private boolean deleteActionForOutboxAndSagaStatuses = false;

    @Override
    public PaymentOutbox save(PaymentOutbox paymentOutbox) {
        deleteActionForOutboxAndSagaStatuses = false;
        return PaymentOutbox.builder()
                .id(paymentOutbox.getId())
                .orderStatus(paymentOutbox.getOrderStatus())
                .outboxStatus(paymentOutbox.getOutboxStatus())
                .payload(paymentOutbox.getPayload())
                .sagaId(paymentOutbox.getSagaId())
                .sagaStatus(paymentOutbox.getSagaStatus())
                .build();
    }

    @Override
    public Optional<List<PaymentOutbox>> findByOutboxStatusAndSagaStatuses(OutboxStatus outboxStatus, SagaStatus... sagaStatuses) {
        if (deleteActionForOutboxAndSagaStatuses) {
            return Optional.of(Collections.EMPTY_LIST);
        }
        return Optional.of(List.of(
                PaymentOutbox.builder()
                        .id(1L)
                        .orderStatus(OrderStatus.CHECKING)
                        .outboxStatus(outboxStatus)
                        .payload("payload")
                        .sagaId(UUID.randomUUID())
                        .sagaStatus(sagaStatuses[0])
                        .build(),
                PaymentOutbox.builder()
                        .id(2L)
                        .orderStatus(OrderStatus.PENDING)
                        .outboxStatus(outboxStatus)
                        .payload("payload2")
                        .sagaId(UUID.randomUUID())
                        .sagaStatus(sagaStatuses[1])
                        .build(),
                PaymentOutbox.builder()
                        .id(3L)
                        .orderStatus(OrderStatus.APPROVED)
                        .outboxStatus(outboxStatus)
                        .payload("payload3")
                        .sagaId(UUID.randomUUID())
                        .sagaStatus(sagaStatuses[2])
                        .build()
        ));
    }

    @Override
    public Optional<PaymentOutbox> findBySagaIdAndSagaStatuses(UUID sagaId, SagaStatus... sagaStatuses) {
        if (!EXIST_SAGA_ID.equals(sagaId)){
            return Optional.empty();
        }
        return Optional.of(
                PaymentOutbox.builder()
                        .id(1L)
                        .orderStatus(OrderStatus.CHECKING)
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
}
