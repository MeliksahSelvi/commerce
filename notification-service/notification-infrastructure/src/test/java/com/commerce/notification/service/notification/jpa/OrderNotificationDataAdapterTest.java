package com.commerce.notification.service.notification.jpa;

import com.commerce.notification.service.adapters.notification.jpa.OrderNotificationDataAdapter;
import com.commerce.notification.service.adapters.notification.jpa.entity.OrderNotificationEntity;
import com.commerce.notification.service.adapters.notification.jpa.repository.OrderNotificationEntityRepository;
import com.commerce.notification.service.common.valueobject.NotificationStatus;
import com.commerce.notification.service.notification.model.OrderNotification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */
@ExtendWith(MockitoExtension.class)
class OrderNotificationDataAdapterTest {

    @InjectMocks
    private OrderNotificationDataAdapter adapter;

    @Mock
    private OrderNotificationEntityRepository repository;

    @Test
    void should_save(){
        //given
        var orderNotification = buildOrderNotification();
        var orderNotificationEntity = mock(OrderNotificationEntity.class);
        when(repository.save(any())).thenReturn(orderNotificationEntity);
        when(orderNotificationEntity.toModel()).thenReturn(orderNotification);

        //when
        var savedOrderNotification = adapter.save(orderNotification);

        //then
        assertEquals(orderNotification.getId(),savedOrderNotification.getId());
        assertEquals(orderNotification.getOrderId(),savedOrderNotification.getOrderId());
        assertEquals(orderNotification.getCustomerId(),savedOrderNotification.getCustomerId());
        assertEquals(orderNotification.getNotificationStatus(),savedOrderNotification.getNotificationStatus());
        assertEquals(orderNotification.getMessage(),savedOrderNotification.getMessage());
    }

    @Test
    void should_findByOrderIdAndNotificationStatus(){
        //given
        OrderNotification orderNotification = buildOrderNotification();
        Long orderId=orderNotification.getOrderId();
        var notificationStatus=orderNotification.getNotificationStatus();

        var orderNotificationEntity=mock(OrderNotificationEntity.class);
        when(repository.findByOrderIdAndNotificationStatus(orderId,notificationStatus)).thenReturn(Optional.of(orderNotificationEntity));
        when(orderNotificationEntity.toModel()).thenReturn(orderNotification);

        //when
        var orderNotificationOptional = adapter.findByOrderIdAndNotificationStatus(orderId, notificationStatus);

        //then
        assertTrue(orderNotificationOptional.isPresent());
        assertEquals(orderNotificationOptional.get().getId(),orderId);
        assertEquals(orderNotificationOptional.get().getNotificationStatus(),notificationStatus);
    }

    @Test
    void should_findByOrderIdAndNotificationStatus_empty(){
        //given
        when(repository.findByOrderIdAndNotificationStatus(anyLong(),any())).thenReturn(Optional.empty());

        //when
        var orderNotificationOptional = adapter.findByOrderIdAndNotificationStatus(anyLong(), any());

        //then
        assertTrue(orderNotificationOptional.isEmpty());
    }

    private OrderNotification buildOrderNotification(){
        return OrderNotification.builder()
                .id(1L)
                .orderId(1L)
                .customerId(1L)
                .message("message")
                .notificationStatus(NotificationStatus.APPROVED)
                .build();
    }

}
