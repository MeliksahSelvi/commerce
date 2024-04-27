package com.commerce.payment.service.adapters.outbox.jpa;

import com.commerce.payment.service.adapters.outbox.jpa.entity.OrderOutboxEntity;
import com.commerce.payment.service.adapters.outbox.jpa.repository.OrderOutboxEntityRepository;
import com.commerce.payment.service.common.outbox.OutboxStatus;
import com.commerce.payment.service.outbox.model.OrderOutbox;
import com.commerce.payment.service.outbox.port.jpa.OrderOutboxDataPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

@Service
public class OrderOutboxDataAdapter implements OrderOutboxDataPort {

    private final OrderOutboxEntityRepository repository;

    public OrderOutboxDataAdapter(OrderOutboxEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public OrderOutbox save(OrderOutbox orderOutbox) {
        var orderOutboxEntity = new OrderOutboxEntity();
        orderOutboxEntity.setId(orderOutbox.getId());
        orderOutboxEntity.setSagaId(orderOutbox.getSagaId());
        orderOutboxEntity.setPayload(orderOutbox.getPayload());
        orderOutboxEntity.setPaymentStatus(orderOutbox.getPaymentStatus());
        orderOutboxEntity.setOutboxStatus(orderOutbox.getOutboxStatus());
        return repository.save(orderOutboxEntity).toModel();
    }

    @Override
    public Optional<List<OrderOutbox>> findByOutboxStatus(OutboxStatus outboxStatus) {
        Optional<List<OrderOutboxEntity>> orderOutboxEntitiesOptional = repository.findByOutboxStatus(outboxStatus);
        return orderOutboxEntitiesOptional.map(orderOutboxEntities -> orderOutboxEntities
                .stream()
                .map(OrderOutboxEntity::toModel)
                .toList());
    }

    @Override
    public void deleteByOutboxStatus(OutboxStatus outboxStatus) {
        repository.deleteByOutboxStatus(outboxStatus);
    }
}
