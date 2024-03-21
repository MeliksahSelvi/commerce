package com.commerce.inventory.service.adapters.outbox.jpa;

import com.commerce.inventory.service.adapters.outbox.jpa.entity.OrderOutboxEntity;
import com.commerce.inventory.service.adapters.outbox.jpa.repository.OrderOutboxEntityRepository;
import com.commerce.inventory.service.common.outbox.OutboxStatus;
import com.commerce.inventory.service.outbox.entity.OrderOutbox;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
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
class OrderOutboxDataAdapterTest {

    @InjectMocks
    private OrderOutboxDataAdapter orderOutboxDataAdapter;

    @Mock
    private OrderOutboxEntityRepository orderOutboxEntityRepository;

    @Test
    void should_save() {
        //given
        var orderOutbox = buildOrderOutbox();
        OrderOutboxEntity orderOutboxEntity = mock(OrderOutboxEntity.class);
        when(orderOutboxEntityRepository.save(any())).thenReturn(orderOutboxEntity);
        when(orderOutboxEntity.toModel()).thenReturn(orderOutbox);

        //when
        var savedOrderOutbox = orderOutboxDataAdapter.save(orderOutbox);

        //then
        assertEquals(orderOutbox.getId(), savedOrderOutbox.getId());
        assertEquals(orderOutbox.getSagaId(), savedOrderOutbox.getSagaId());
        assertEquals(orderOutbox.getPayload(), savedOrderOutbox.getPayload());
        assertEquals(orderOutbox.getOutboxStatus(), savedOrderOutbox.getOutboxStatus());
    }

    @Test
    void should_findByOutboxStatus() {
        //given
        OutboxStatus outboxStatus = OutboxStatus.STARTED;
        List<OrderOutboxEntity> list = buildOrderOutboxEntityList();
        when(orderOutboxEntityRepository.findByOutboxStatus(outboxStatus)).thenReturn(Optional.of(list));

        //when
        Optional<List<OrderOutbox>> orderOutboxList = orderOutboxDataAdapter.findByOutboxStatus(outboxStatus);

        //then
        assertFalse(orderOutboxList.isEmpty());
        assertTrue(orderOutboxList.get().stream().map(OrderOutbox::getOutboxStatus).allMatch(outboxStatus::equals));
    }

    @Test
    void should_findByOutboxStatus_empty() {
        //given
        OutboxStatus outboxStatus = OutboxStatus.STARTED;
        when(orderOutboxEntityRepository.findByOutboxStatus(outboxStatus)).thenReturn(Optional.empty());

        //when
        Optional<List<OrderOutbox>> orderOutboxList = orderOutboxDataAdapter.findByOutboxStatus(outboxStatus);

        //then
        assertTrue(orderOutboxList.isEmpty());
    }

    @Test
    void should_deleteByOutboxStatus() {
        //given
        OutboxStatus outboxStatus = OutboxStatus.STARTED;

        //when
        orderOutboxDataAdapter.deleteByOutboxStatus(outboxStatus);

        List<OrderOutboxEntity> entityList = orderOutboxEntityRepository.findAll();
        assertTrue(entityList.isEmpty());
    }

    private List<OrderOutboxEntity> buildOrderOutboxEntityList() {
        return List.of(buildOrderOutboxEntity());
    }

    private OrderOutboxEntity buildOrderOutboxEntity() {
        var orderOutboxEntity = new OrderOutboxEntity();
        orderOutboxEntity.setId(1L);
        orderOutboxEntity.setSagaId(UUID.randomUUID());
        orderOutboxEntity.setPayload("payload");
        orderOutboxEntity.setOutboxStatus(OutboxStatus.STARTED);
        return orderOutboxEntity;
    }

    private OrderOutbox buildOrderOutbox() {
        return OrderOutbox.builder()
                .id(1L)
                .sagaId(UUID.randomUUID())
                .payload("payload")
                .outboxStatus(OutboxStatus.STARTED)
                .build();
    }
}
