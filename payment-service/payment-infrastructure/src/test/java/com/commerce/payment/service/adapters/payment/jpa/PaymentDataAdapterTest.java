package com.commerce.payment.service.adapters.payment.jpa;

import com.commerce.payment.service.adapters.payment.jpa.entity.PaymentEntity;
import com.commerce.payment.service.adapters.payment.jpa.repository.PaymentEntityRepository;
import com.commerce.payment.service.common.valueobject.Money;
import com.commerce.payment.service.common.valueobject.PaymentStatus;
import com.commerce.payment.service.payment.entity.Payment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

@ExtendWith(MockitoExtension.class)
class PaymentDataAdapterTest {

    @InjectMocks
    private PaymentDataAdapter adapter;

    @Mock
    private PaymentEntityRepository repository;

    @Test
    void should_save() {
        var payment = buildPayment();
        var paymentEntity = mock(PaymentEntity.class);
        when(repository.save(any())).thenReturn(paymentEntity);
        when(paymentEntity.toModel()).thenReturn(payment);

        //when
        var savedPayment = adapter.save(payment);

        //then
        assertNotNull(savedPayment);
        assertEquals(payment.getId(), savedPayment.getId());
        assertEquals(payment.getCustomerId(), savedPayment.getCustomerId());
        assertEquals(payment.getPaymentStatus(), savedPayment.getPaymentStatus());
        assertEquals(payment.getOrderId(), savedPayment.getOrderId());
        assertEquals(payment.getCost(), savedPayment.getCost());
    }

    @Test
    void should_findByOrderId() {
        //given
        var payment = buildPayment();
        var paymentEntity = mock(PaymentEntity.class);
        when(repository.findByOrderId(any())).thenReturn(Optional.of(paymentEntity));
        when(paymentEntity.toModel()).thenReturn(payment);

        //when
        var paymentOptional = adapter.findByOrderId(payment.getOrderId());

        //then
        assertTrue(paymentOptional.isPresent());
        assertEquals(payment.getOrderId(), paymentOptional.get().getOrderId());
    }


    @Test
    void should_findByOrderId_empty() {
        //given
        when(repository.findByOrderId(any())).thenReturn(Optional.empty());

        //when
        var paymentOptional = adapter.findByOrderId(any());

        //then
        assertTrue(paymentOptional.isEmpty());
    }

    private Payment buildPayment() {
        return Payment.builder()
                .id(1L)
                .cost(new Money(BigDecimal.TEN))
                .paymentstatus(PaymentStatus.COMPLETED)
                .customerId(1L)
                .orderId(1L)
                .build();
    }
}
