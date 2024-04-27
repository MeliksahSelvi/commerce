package com.commerce.order.service.adapters.outbox.jpa;

import com.commerce.order.service.adapters.outbox.jpa.entity.InventoryOutboxEntity;
import com.commerce.order.service.adapters.outbox.jpa.repository.InventoryOutboxEntityRepository;
import com.commerce.order.service.common.outbox.OutboxStatus;
import com.commerce.order.service.common.saga.SagaStatus;
import com.commerce.order.service.common.valueobject.OrderInventoryStatus;
import com.commerce.order.service.outbox.model.InventoryOutbox;
import com.commerce.order.service.outbox.port.jpa.InventoryOutboxDataPort;
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
    public Optional<List<InventoryOutbox>> findByOutboxStatusAndSagaStatusAndOrderInventoryStatus(OutboxStatus outboxStatus, SagaStatus sagaStatus, OrderInventoryStatus orderInventoryStatus) {
        Optional<List<InventoryOutboxEntity>> inventoryOutboxEntitiesOptional = inventoryOutboxEntityRepository
                .findByOutboxStatusAndSagaStatusAndOrderInventoryStatus(outboxStatus, sagaStatus, orderInventoryStatus);

        return inventoryOutboxEntitiesOptional.map(inventoryOutboxEntities -> inventoryOutboxEntities.stream()
                .map(InventoryOutboxEntity::toModel)
                .toList());
    }

    @Override
    public Optional<InventoryOutbox> findBySagaIdAndSagaStatusAndOrderInventoryStatus(UUID sagaId, SagaStatus sagaStatus, OrderInventoryStatus orderInventoryStatus) {
        Optional<InventoryOutboxEntity> inventoryOutboxEntityOptional = inventoryOutboxEntityRepository
                .findBySagaIdAndSagaStatusAndOrderInventoryStatus(sagaId, sagaStatus, orderInventoryStatus);
        return inventoryOutboxEntityOptional.map(InventoryOutboxEntity::toModel);
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
    public void deleteByOutboxStatusAndSagaStatusAndOrderInventoryStatus(OutboxStatus outboxStatus, SagaStatus sagaStatus, OrderInventoryStatus orderInventoryStatus) {
        inventoryOutboxEntityRepository.deleteByOutboxStatusAndSagaStatusAndOrderInventoryStatus(outboxStatus, sagaStatus, orderInventoryStatus);
    }
}
