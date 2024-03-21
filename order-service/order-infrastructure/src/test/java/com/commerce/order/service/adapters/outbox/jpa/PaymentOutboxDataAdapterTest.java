package com.commerce.order.service.adapters.outbox.jpa;

import com.commerce.order.service.adapters.outbox.jpa.entity.InventoryOutboxEntity;
import com.commerce.order.service.adapters.outbox.jpa.entity.PaymentOutboxEntity;
import com.commerce.order.service.adapters.outbox.jpa.repository.PaymentOutboxEntityRepository;
import com.commerce.order.service.common.outbox.OutboxStatus;
import com.commerce.order.service.common.saga.SagaStatus;
import com.commerce.order.service.common.valueobject.OrderInventoryStatus;
import com.commerce.order.service.common.valueobject.OrderStatus;
import com.commerce.order.service.outbox.entity.InventoryOutbox;
import com.commerce.order.service.outbox.entity.PaymentOutbox;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

@ExtendWith(MockitoExtension.class)
class PaymentOutboxDataAdapterTest {

    @InjectMocks
    private PaymentOutboxDataAdapter adapter;

    @Mock
    private PaymentOutboxEntityRepository repository;

    @Test
    void should_save(){
        //given
        var paymentOutbox = buildPaymentOutbox();
        var paymentOutboxEntity = mock(PaymentOutboxEntity.class);
        when(repository.save(any())).thenReturn(paymentOutboxEntity);
        when(paymentOutboxEntity.toModel()).thenReturn(paymentOutbox);

        //when
        PaymentOutbox savedPaymentOutbox = adapter.save(paymentOutbox);

        //then
        assertNotNull(savedPaymentOutbox);
        assertEquals(savedPaymentOutbox.getId(), paymentOutbox.getId());
        assertEquals(savedPaymentOutbox.getOutboxStatus(), paymentOutbox.getOutboxStatus());
        assertEquals(savedPaymentOutbox.getPayload(), paymentOutbox.getPayload());
        assertEquals(savedPaymentOutbox.getOrderStatus(), paymentOutbox.getOrderStatus());
        assertEquals(savedPaymentOutbox.getSagaId(), paymentOutbox.getSagaId());
        assertEquals(savedPaymentOutbox.getSagaStatus(), paymentOutbox.getSagaStatus());
    }

    @Test
    void should_findByOutboxStatusAndSagaStatuses(){
        //given
        var paymentOutbox = buildPaymentOutbox();

        var paymentOutboxEntity = mock(PaymentOutboxEntity.class);
        var paymentOutboxEntities = new ArrayList<PaymentOutboxEntity>();
        paymentOutboxEntities.add(paymentOutboxEntity);

        when(repository.findByOutboxStatusAndSagaStatusIn(any(), any())).thenReturn(Optional.of(paymentOutboxEntities));
        when(paymentOutboxEntity.toModel()).thenReturn(paymentOutbox);

        //when
        var inventoryOutboxList = adapter.
                findByOutboxStatusAndSagaStatuses(paymentOutbox.getOutboxStatus(), paymentOutbox.getSagaStatus());

        //then
        assertTrue(inventoryOutboxList.isPresent());
        assertTrue(inventoryOutboxList.get().size() > 0);
        assertTrue(inventoryOutboxList.get()
                .stream()
                .map(PaymentOutbox::getOutboxStatus)
                .allMatch(paymentOutbox.getOutboxStatus()::equals));
        assertTrue(inventoryOutboxList.get()
                .stream()
                .map(PaymentOutbox::getSagaStatus)
                .anyMatch(paymentOutbox.getSagaStatus()::equals));
    }

    @Test
    void should_findByOutboxStatusAndSagaStatuses_empty(){
        //given
        when(repository.findByOutboxStatusAndSagaStatusIn(any(), any())).thenReturn(Optional.empty());

        //when
        var paymentOutboxList = adapter.findByOutboxStatusAndSagaStatuses(OutboxStatus.COMPLETED, SagaStatus.PAYING);

        //then
        assertTrue(paymentOutboxList.isEmpty());
    }

    @Test
    void should_findBySagaIdAndSagaStatuses(){
        //given
        var paymentOutbox = buildPaymentOutbox();

        var paymentOutboxEntity = mock(PaymentOutboxEntity.class);
        when(paymentOutboxEntity.toModel()).thenReturn(paymentOutbox);
        when(repository.findBySagaIdAndSagaStatusIn(any(), any())).thenReturn(Optional.of(paymentOutboxEntity));

        //when
        var paymentOutboxOptional = adapter.findBySagaIdAndSagaStatuses(paymentOutbox.getSagaId(), paymentOutbox.getSagaStatus());

        //then
        assertTrue(paymentOutboxOptional.isPresent());
        assertNotNull(paymentOutboxOptional.get());
        assertEquals(paymentOutboxOptional.get().getSagaId(), paymentOutbox.getSagaId());
        assertEquals(paymentOutboxOptional.get().getSagaStatus(), paymentOutbox.getSagaStatus());
    }

    @Test
    void should_findBySagaIdAndSagaStatuses_empty(){
        //given
        when(repository.findBySagaIdAndSagaStatusIn(any(), any())).thenReturn(Optional.empty());

        //when
        var paymentOutbox = adapter.findBySagaIdAndSagaStatuses(UUID.randomUUID(), SagaStatus.PAYING);

        //then
        assertTrue(paymentOutbox.isEmpty());
    }

    @Test
    void should_deleteByOutboxStatusAndSagaStatuses(){
        //when
        adapter.deleteByOutboxStatusAndSagaStatuses(OutboxStatus.STARTED, SagaStatus.PAYING);

        //then
        var entityList = repository.findAll();
        assertTrue(entityList.isEmpty());
    }

    private PaymentOutbox buildPaymentOutbox() {
        return PaymentOutbox.builder()
                .id(1L)
                .sagaId(UUID.randomUUID())
                .orderStatus(OrderStatus.APPROVED)
                .sagaStatus(SagaStatus.CHECKING)
                .outboxStatus(OutboxStatus.COMPLETED)
                .payload("payload")
                .build();
    }
}
