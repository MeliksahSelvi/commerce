package com.commerce.inventory.service.adapters.outbox.jpa;

import com.commerce.inventory.service.adapters.outbox.jpa.entity.OrderOutboxEntity;
import com.commerce.inventory.service.adapters.outbox.jpa.repository.OrderOutboxEntityRepository;
import com.commerce.inventory.service.common.outbox.OutboxStatus;
import com.commerce.inventory.service.outbox.model.OrderOutbox;
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
class OrderOutboxDataAdapterTest {

    @InjectMocks
    private OrderOutboxDataAdapter orderOutboxDataAdapter;

    @Mock
    private OrderOutboxEntityRepository orderOutboxEntityRepository;

    @Test
    void should_save() {
        //given
        var orderOutbox = buildOrderOutbox();
        var orderOutboxEntity = mock(OrderOutboxEntity.class);
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
        var orderOutbox = buildOrderOutbox();

        var orderOutboxEntity = mock(OrderOutboxEntity.class);
        when(orderOutboxEntity.toModel()).thenReturn(orderOutbox);
        var list = new ArrayList<OrderOutboxEntity>();
        list.add(orderOutboxEntity);

        when(orderOutboxEntityRepository.findByOutboxStatus(any())).thenReturn(Optional.of(list));

        //when
        var orderOutboxList = orderOutboxDataAdapter.findByOutboxStatus(any());

        //then
        assertFalse(orderOutboxList.isEmpty());
        assertTrue(orderOutboxList.get().stream().map(OrderOutbox::getOutboxStatus).allMatch(orderOutbox.getOutboxStatus()::equals));
    }

    @Test
    void should_findByOutboxStatus_empty() {
        //given
        when(orderOutboxEntityRepository.findByOutboxStatus(any())).thenReturn(Optional.empty());

        //when
        var orderOutboxList = orderOutboxDataAdapter.findByOutboxStatus(any());

        //then
        assertTrue(orderOutboxList.isEmpty());
    }

    @Test
    void should_deleteByOutboxStatus() {
        //when
        orderOutboxDataAdapter.deleteByOutboxStatus(any());

        //then
        var entityList = orderOutboxEntityRepository.findAll();
        assertTrue(entityList.isEmpty());
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
