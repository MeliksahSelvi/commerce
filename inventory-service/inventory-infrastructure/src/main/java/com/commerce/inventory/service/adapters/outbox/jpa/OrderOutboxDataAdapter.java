package com.commerce.inventory.service.adapters.outbox.jpa;

import com.commerce.inventory.service.adapters.outbox.jpa.entity.OrderOutboxEntity;
import com.commerce.inventory.service.adapters.outbox.jpa.repository.OrderOutboxEntityRepository;
import com.commerce.inventory.service.common.outbox.OutboxStatus;
import com.commerce.inventory.service.outbox.entity.OrderOutbox;
import com.commerce.inventory.service.outbox.port.jpa.OrderOutboxDataPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

@Service
public class OrderOutboxDataAdapter implements OrderOutboxDataPort {

    private final OrderOutboxEntityRepository orderOutboxEntityRepository;

    public OrderOutboxDataAdapter(OrderOutboxEntityRepository orderOutboxEntityRepository) {
        this.orderOutboxEntityRepository = orderOutboxEntityRepository;
    }

    @Override
    public OrderOutbox save(OrderOutbox orderOutbox) {
        var inventoryOutboxEntity = new OrderOutboxEntity();
        inventoryOutboxEntity.setId(orderOutbox.getId());
        inventoryOutboxEntity.setSagaId(orderOutbox.getSagaId());
        inventoryOutboxEntity.setPayload(orderOutbox.getPayload());
        inventoryOutboxEntity.setOutboxStatus(orderOutbox.getOutboxStatus());
        return orderOutboxEntityRepository.save(inventoryOutboxEntity).toModel();
    }

    @Override
    public Optional<List<OrderOutbox>> findByOutboxStatus(OutboxStatus outboxStatus) {
        Optional<List<OrderOutboxEntity>> orderOutboxEntitiesOptional = orderOutboxEntityRepository
                .findByOutboxStatus(outboxStatus);

        return orderOutboxEntitiesOptional.map(orderOutboxEntities -> orderOutboxEntities.stream()
                .map(OrderOutboxEntity::toModel)
                .toList());
    }

    @Override
    public void deleteByOutboxStatus(OutboxStatus outboxStatus) {
        orderOutboxEntityRepository.deleteByOutboxStatus(outboxStatus);
    }
}
