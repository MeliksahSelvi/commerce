package com.commerce.order.service.adapters.outbox.jpa;

import com.commerce.order.service.adapters.outbox.jpa.entity.InventoryOutboxEntity;
import com.commerce.order.service.adapters.outbox.jpa.repository.InventoryOutboxEntityRepository;
import com.commerce.order.service.common.outbox.OutboxStatus;
import com.commerce.order.service.common.saga.SagaStatus;
import com.commerce.order.service.common.valueobject.OrderInventoryStatus;
import com.commerce.order.service.outbox.entity.InventoryOutbox;
import com.commerce.order.service.outbox.port.jpa.InventoryOutboxDataPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @Author mselvi
 * @Created 06.03.2024
 */

@Service
public class InventoryOutboxDataAdapter implements InventoryOutboxDataPort {

    private static final Logger logger = LoggerFactory.getLogger(InventoryOutboxDataAdapter.class);
    private final InventoryOutboxEntityRepository inventoryOutboxEntityRepository;

    public InventoryOutboxDataAdapter(InventoryOutboxEntityRepository inventoryOutboxEntityRepository) {
        this.inventoryOutboxEntityRepository = inventoryOutboxEntityRepository;
    }

    @Override
    public InventoryOutbox save(InventoryOutbox inventoryOutbox) {
        var inventoryOutboxEntity = new InventoryOutboxEntity();
        inventoryOutboxEntity.setId(inventoryOutbox.getId());
        inventoryOutboxEntity.setSagaId(inventoryOutbox.getSagaId());
        inventoryOutboxEntity.setPayload(inventoryOutbox.getPayload());
        inventoryOutboxEntity.setOrderStatus(inventoryOutbox.getOrderStatus());
        inventoryOutboxEntity.setSagaStatus(inventoryOutbox.getSagaStatus());
        inventoryOutboxEntity.setOrderInventoryStatus(inventoryOutbox.getOrderInventoryStatus());
        inventoryOutboxEntity.setOutboxStatus(inventoryOutbox.getOutboxStatus());
        return inventoryOutboxEntityRepository.save(inventoryOutboxEntity).toModel();
    }

    @Override
    public Optional<List<InventoryOutbox>> findByOutboxStatusAndSagaStatuses(OutboxStatus outboxStatus, SagaStatus... sagaStatuses) {
        Optional<List<InventoryOutboxEntity>> inventoryOutboxEntitiesOptional = inventoryOutboxEntityRepository
                .findByOutboxStatusAndSagaStatusIn(outboxStatus, List.of(sagaStatuses));

        return inventoryOutboxEntitiesOptional.map(inventoryOutboxEntities -> inventoryOutboxEntities.stream()
                .map(InventoryOutboxEntity::toModel)
                .toList());
    }

    @Override
    public Optional<List<InventoryOutbox>> findByOutboxStatusAndSagaStatusAndOrderInventoryStatuses(OutboxStatus outboxStatus, SagaStatus sagaStatus, OrderInventoryStatus... orderInventoryStatuses) {
        Optional<List<InventoryOutboxEntity>> inventoryOutboxEntitiesOptional = inventoryOutboxEntityRepository
                .findByOutboxStatusAndSagaStatusAndOrderInventoryStatusIn(outboxStatus, sagaStatus, List.of(orderInventoryStatuses));

        return inventoryOutboxEntitiesOptional.map(inventoryOutboxEntities -> inventoryOutboxEntities.stream()
                .map(InventoryOutboxEntity::toModel)
                .toList());
    }

    @Override
    public Optional<InventoryOutbox> findBySagaIdAndSagaStatuses(UUID sagaId, SagaStatus... sagaStatuses) {
        Optional<InventoryOutboxEntity> inventoryOutboxEntityOptional = inventoryOutboxEntityRepository
                .findBySagaIdAndSagaStatusIn(sagaId, List.of(sagaStatuses));

        return inventoryOutboxEntityOptional.map(InventoryOutboxEntity::toModel);
    }

    @Override
    public void deleteByOutboxStatusAndSagaStatuses(OutboxStatus outboxStatus, SagaStatus... sagaStatuses) {
        inventoryOutboxEntityRepository.deleteByOutboxStatusAndSagaStatusIn(outboxStatus, List.of(sagaStatuses));
    }

    @Override
    public void deleteByOutboxStatusAndSagaStatusAndOrderInventoryStatuses(OutboxStatus outboxStatus, SagaStatus sagaStatus, OrderInventoryStatus... orderInventoryStatuses) {
        inventoryOutboxEntityRepository.deleteByOutboxStatusAndSagaStatusAndOrderInventoryStatusIn(outboxStatus,sagaStatus,List.of(orderInventoryStatuses));
    }
}
