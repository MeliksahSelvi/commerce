package com.commerce.order.service.adapters.outbox.jpa;

import com.commerce.order.service.adapters.outbox.jpa.entity.PaymentOutboxEntity;
import com.commerce.order.service.adapters.outbox.jpa.repository.PaymentOutboxEntityRepository;
import com.commerce.order.service.common.outbox.OutboxStatus;
import com.commerce.order.service.common.saga.SagaStatus;
import com.commerce.order.service.outbox.entity.PaymentOutbox;
import com.commerce.order.service.outbox.port.jpa.PaymentOutboxDataPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @Author mselvi
 * @Created 06.03.2024
 */

@Service
public class PaymentOutboxDataAdapter implements PaymentOutboxDataPort {

    private final PaymentOutboxEntityRepository paymentOutboxEntityRepository;

    public PaymentOutboxDataAdapter(PaymentOutboxEntityRepository paymentOutboxEntityRepository) {
        this.paymentOutboxEntityRepository = paymentOutboxEntityRepository;
    }

    @Override
    public PaymentOutbox save(PaymentOutbox paymentOutbox) {
        var paymentOutboxEntity = new PaymentOutboxEntity();
        paymentOutboxEntity.setId(paymentOutbox.getId());
        paymentOutboxEntity.setSagaId(paymentOutbox.getSagaId());
        paymentOutboxEntity.setPayload(paymentOutbox.getPayload());
        paymentOutboxEntity.setOrderStatus(paymentOutbox.getOrderStatus());
        paymentOutboxEntity.setSagaStatus(paymentOutbox.getSagaStatus());
        paymentOutboxEntity.setOutboxStatus(paymentOutbox.getOutboxStatus());
        return paymentOutboxEntityRepository.save(paymentOutboxEntity).toModel();
    }

    @Override
    public Optional<List<PaymentOutbox>> findByOutboxStatusAndSagaStatuses(OutboxStatus outboxStatus, SagaStatus... sagaStatuses) {
        Optional<List<PaymentOutboxEntity>> paymentOutboxEntitiesOptional = paymentOutboxEntityRepository
                .findByOutboxStatusAndSagaStatusIn(outboxStatus, List.of(sagaStatuses));

        return paymentOutboxEntitiesOptional.map(paymentOutboxEntities -> paymentOutboxEntities
                .stream()
                .map(PaymentOutboxEntity::toModel)
                .toList());
    }

    @Override
    public Optional<PaymentOutbox> findBySagaIdAndSagaStatuses(UUID sagaId, SagaStatus... sagaStatuses) {
        Optional<PaymentOutboxEntity> paymentOutboxEntityOptional = paymentOutboxEntityRepository
                .findBySagaIdAndSagaStatusIn(sagaId, List.of(sagaStatuses));

        return paymentOutboxEntityOptional.map(PaymentOutboxEntity::toModel);
    }

    @Override
    public void deleteByOutboxStatusAndSagaStatuses(OutboxStatus outboxStatus, SagaStatus... sagaStatuses) {
        paymentOutboxEntityRepository.deleteByOutboxStatusAndSagaStatusIn(outboxStatus, List.of(sagaStatuses));
    }
}
