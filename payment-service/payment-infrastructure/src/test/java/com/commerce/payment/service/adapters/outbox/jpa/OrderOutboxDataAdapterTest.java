package com.commerce.payment.service.adapters.outbox.jpa;

import com.commerce.payment.service.adapters.outbox.jpa.entity.OrderOutboxEntity;
import com.commerce.payment.service.adapters.outbox.jpa.repository.OrderOutboxEntityRepository;
import com.commerce.payment.service.common.outbox.OutboxStatus;
import com.commerce.payment.service.outbox.entity.OrderOutbox;
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
    private OrderOutboxDataAdapter adapter;

    @Mock
    private OrderOutboxEntityRepository repository;

    @Test
    void should_save() {
        var orderOutbox = buildOrderOutbox();
        var orderOutboxEntity = mock(OrderOutboxEntity.class);
        when(repository.save(any())).thenReturn(orderOutboxEntity);
        when(orderOutboxEntity.toModel()).thenReturn(orderOutbox);

        //when
        OrderOutbox savedPaymentOutbox = adapter.save(orderOutbox);

        //then
        assertNotNull(savedPaymentOutbox);
        assertEquals(savedPaymentOutbox.getId(), orderOutbox.getId());
        assertEquals(savedPaymentOutbox.getOutboxStatus(), orderOutbox.getOutboxStatus());
        assertEquals(savedPaymentOutbox.getPayload(), orderOutbox.getPayload());
        assertEquals(savedPaymentOutbox.getSagaId(), orderOutbox.getSagaId());
    }

    @Test
    void should_findByOutboxStatus() {
        //given
        var orderOutbox = buildOrderOutbox();

        var orderOutboxEntity = mock(OrderOutboxEntity.class);
        when(orderOutboxEntity.toModel()).thenReturn(orderOutbox);
        var list = new ArrayList<OrderOutboxEntity>();
        list.add(orderOutboxEntity);

        when(repository.findByOutboxStatus(any())).thenReturn(Optional.of(list));

        //when
        var orderOutboxList = adapter.findByOutboxStatus(any());

        //then
        assertFalse(orderOutboxList.isEmpty());
        assertTrue(orderOutboxList.get()
                .stream()
                .map(OrderOutbox::getOutboxStatus)
                .allMatch(orderOutbox.getOutboxStatus()::equals));
    }

    @Test
    void should_findByOutboxStatus_empty() {
        //given
        when(repository.findByOutboxStatus(any())).thenReturn(Optional.empty());

        //when
        var orderOutboxList = adapter.findByOutboxStatus(any());

        //then
        assertTrue(orderOutboxList.isEmpty());
    }

    @Test
    void should_deleteByOutboxStatus() {
        //when
        adapter.deleteByOutboxStatus(any());

        //then
        var entityList = repository.findAll();
        assertTrue(entityList.isEmpty());
    }

    private OrderOutbox buildOrderOutbox() {
        return OrderOutbox.builder()
                .id(1L)
                .sagaId(UUID.randomUUID())
                .outboxStatus(OutboxStatus.COMPLETED)
                .payload("payload")
                .build();
    }
}
