package com.commerce.order.service.adapters.outbox.jpa;

import com.commerce.order.service.adapters.outbox.jpa.entity.InventoryOutboxEntity;
import com.commerce.order.service.adapters.outbox.jpa.repository.InventoryOutboxEntityRepository;
import com.commerce.order.service.common.outbox.OutboxStatus;
import com.commerce.order.service.common.saga.SagaStatus;
import com.commerce.order.service.common.valueobject.OrderInventoryStatus;
import com.commerce.order.service.common.valueobject.OrderStatus;
import com.commerce.order.service.outbox.entity.InventoryOutbox;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

@ExtendWith(MockitoExtension.class)
class InventoryOutboxDataAdapterTest {

    @InjectMocks
    private InventoryOutboxDataAdapter adapter;

    @Mock
    private InventoryOutboxEntityRepository repository;

    @Test
    void should_save() {
        //given
        var inventoryOutbox = buildInventoryOutbox();
        var inventoryOutboxEntity = mock(InventoryOutboxEntity.class);
        when(repository.save(any())).thenReturn(inventoryOutboxEntity);
        when(inventoryOutboxEntity.toModel()).thenReturn(inventoryOutbox);

        //when
        InventoryOutbox savedInventoryOutbox = adapter.save(inventoryOutbox);

        //then
        assertNotNull(savedInventoryOutbox);
        assertEquals(savedInventoryOutbox.getId(), inventoryOutbox.getId());
        assertEquals(savedInventoryOutbox.getOutboxStatus(), inventoryOutbox.getOutboxStatus());
        assertEquals(savedInventoryOutbox.getOrderInventoryStatus(), inventoryOutbox.getOrderInventoryStatus());
        assertEquals(savedInventoryOutbox.getPayload(), inventoryOutbox.getPayload());
        assertEquals(savedInventoryOutbox.getOrderStatus(), inventoryOutbox.getOrderStatus());
        assertEquals(savedInventoryOutbox.getSagaId(), inventoryOutbox.getSagaId());
        assertEquals(savedInventoryOutbox.getSagaStatus(), inventoryOutbox.getSagaStatus());
    }

    @Test
    void should_findByOutboxStatusAndSagaStatuses() {
        //given
        var inventoryOutbox = buildInventoryOutbox();

        var inventoryOutboxEntity = mock(InventoryOutboxEntity.class);
        var inventoryOutboxEntityList = new ArrayList<InventoryOutboxEntity>();
        inventoryOutboxEntityList.add(inventoryOutboxEntity);

        when(repository.findByOutboxStatusAndSagaStatusIn(any(), any())).thenReturn(Optional.of(inventoryOutboxEntityList));
        when(inventoryOutboxEntity.toModel()).thenReturn(inventoryOutbox);

        //when
        var inventoryOutboxList = adapter.
                findByOutboxStatusAndSagaStatuses(inventoryOutbox.getOutboxStatus(), inventoryOutbox.getSagaStatus());

        //then
        assertTrue(inventoryOutboxList.isPresent());
        assertTrue(inventoryOutboxList.get().size() > 0);
        assertTrue(inventoryOutboxList.get()
                .stream()
                .map(InventoryOutbox::getOutboxStatus)
                .allMatch(inventoryOutbox.getOutboxStatus()::equals));
        assertTrue(inventoryOutboxList.get()
                .stream()
                .map(InventoryOutbox::getSagaStatus)
                .anyMatch(inventoryOutbox.getSagaStatus()::equals));
    }

    @Test
    void should_findByOutboxStatusAndSagaStatuses_empty() {
        //given
        when(repository.findByOutboxStatusAndSagaStatusIn(any(), any())).thenReturn(Optional.empty());

        //when
        var inventoryOutboxList = adapter.findByOutboxStatusAndSagaStatuses(OutboxStatus.COMPLETED, SagaStatus.PAYING);

        //then
        assertTrue(inventoryOutboxList.isEmpty());
    }

    @Test
    void should_findByOutboxStatusAndSagaStatusAndOrderInventoryStatus() {
        //given
        var inventoryOutbox = buildInventoryOutbox();

        var inventoryOutboxEntity = mock(InventoryOutboxEntity.class);
        when(inventoryOutboxEntity.toModel()).thenReturn(inventoryOutbox);
        var list = new ArrayList<InventoryOutboxEntity>();
        list.add(inventoryOutboxEntity);

        when(repository.findByOutboxStatusAndSagaStatusAndOrderInventoryStatus(any(), any(), any())).thenReturn(Optional.of(list));


        //when
        var inventoryOutboxList = adapter.findByOutboxStatusAndSagaStatusAndOrderInventoryStatus(
                any(), any(), any());

        //then
        assertTrue(inventoryOutboxList.isPresent());
        assertTrue(inventoryOutboxList.get().size() > 0);
        assertTrue(inventoryOutboxList.get()
                .stream()
                .map(InventoryOutbox::getOutboxStatus)
                .allMatch(inventoryOutbox.getOutboxStatus()::equals));
        assertTrue(inventoryOutboxList.get()
                .stream()
                .map(InventoryOutbox::getSagaStatus)
                .allMatch(inventoryOutbox.getSagaStatus()::equals));
        assertTrue(inventoryOutboxList.get()
                .stream()
                .map(InventoryOutbox::getOrderInventoryStatus)
                .allMatch(inventoryOutbox.getOrderInventoryStatus()::equals));
    }

    @Test
    void should_findByOutboxStatusAndSagaStatusAndOrderInventoryStatus_empty() {
        //given
        when(repository.findByOutboxStatusAndSagaStatusAndOrderInventoryStatus(any(), any(), any())).thenReturn(Optional.empty());

        //when
        var inventoryOutboxList = adapter.findByOutboxStatusAndSagaStatusAndOrderInventoryStatus(any(), any(), any());

        //then
        assertTrue(inventoryOutboxList.isEmpty());
    }

    @Test
    void should_findBySagaIdAndSagaStatusAndOrderInventoryStatus() {
        //given
        var inventoryOutbox = buildInventoryOutbox();

        var inventoryOutboxEntity = mock(InventoryOutboxEntity.class);
        when(inventoryOutboxEntity.toModel()).thenReturn(inventoryOutbox);
        when(repository.findBySagaIdAndSagaStatusAndOrderInventoryStatus(any(), any(), any())).thenReturn(Optional.of(inventoryOutboxEntity));

        //when
        var inventoryOutboxOptional = adapter.findBySagaIdAndSagaStatusAndOrderInventoryStatus(any(), any(), any());

        //then
        assertTrue(inventoryOutboxOptional.isPresent());
        assertNotNull(inventoryOutboxOptional.get());
        assertEquals(inventoryOutboxOptional.get().getSagaId(), inventoryOutbox.getSagaId());
        assertEquals(inventoryOutboxOptional.get().getSagaStatus(), inventoryOutbox.getSagaStatus());
        assertEquals(inventoryOutboxOptional.get().getOrderInventoryStatus(), inventoryOutbox.getOrderInventoryStatus());
    }

    @Test
    void should_findBySagaIdAndSagaStatusAndOrderInventoryStatus_empty() {
        //given
        when(repository.findBySagaIdAndSagaStatusAndOrderInventoryStatus(any(), any(), any())).thenReturn(Optional.empty());

        //when
        var inventoryOutbox = adapter.findBySagaIdAndSagaStatusAndOrderInventoryStatus(any(), any(), any());

        //then
        assertTrue(inventoryOutbox.isEmpty());
    }

    @Test
    void should_findBySagaIdAndSagaStatuses() {
        //given
        var inventoryOutbox = buildInventoryOutbox();

        var inventoryOutboxEntity = mock(InventoryOutboxEntity.class);
        when(inventoryOutboxEntity.toModel()).thenReturn(inventoryOutbox);
        when(repository.findBySagaIdAndSagaStatusIn(any(), any())).thenReturn(Optional.of(inventoryOutboxEntity));

        //when
        var inventoryOutboxOptional = adapter.findBySagaIdAndSagaStatuses(inventoryOutbox.getSagaId(), inventoryOutbox.getSagaStatus());

        //then
        assertTrue(inventoryOutboxOptional.isPresent());
        assertNotNull(inventoryOutboxOptional.get());
        assertEquals(inventoryOutboxOptional.get().getSagaId(), inventoryOutbox.getSagaId());
        assertEquals(inventoryOutboxOptional.get().getSagaStatus(), inventoryOutbox.getSagaStatus());
    }

    @Test
    void should_findBySagaIdAndSagaStatuses_empty() {
        //given
        when(repository.findBySagaIdAndSagaStatusIn(any(), any())).thenReturn(Optional.empty());

        //when
        var inventoryOutbox = adapter.findBySagaIdAndSagaStatuses(UUID.randomUUID(), SagaStatus.PAYING);

        //then
        assertTrue(inventoryOutbox.isEmpty());
    }

    @Test
    void should_deleteByOutboxStatusAndSagaStatuses() {
        //when
        adapter.deleteByOutboxStatusAndSagaStatuses(OutboxStatus.STARTED, SagaStatus.PAYING);

        //then
        var entityList = repository.findAll();
        assertTrue(entityList.isEmpty());
    }

    @Test
    void should_deleteByOutboxStatusAndSagaStatusAndOrderInventoryStatus() {
        //when
        adapter.deleteByOutboxStatusAndSagaStatusAndOrderInventoryStatus(any(), any(), any());

        //then
        var entityList = repository.findAll();
        assertTrue(entityList.isEmpty());
    }


    private InventoryOutbox buildInventoryOutbox() {
        return InventoryOutbox.builder()
                .id(1L)
                .sagaId(UUID.randomUUID())
                .orderStatus(OrderStatus.APPROVED)
                .orderInventoryStatus(OrderInventoryStatus.CHECKING)
                .sagaStatus(SagaStatus.CHECKING)
                .outboxStatus(OutboxStatus.COMPLETED)
                .payload("payload")
                .build();
    }
}
