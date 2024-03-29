package com.commerce.order.service.adapters.order.messaging.publisher;

import com.commerce.kafka.model.NotificationRequestAvroModel;
import com.commerce.kafka.producer.KafkaProducerWithoutCallback;
import com.commerce.order.service.common.valueobject.Money;
import com.commerce.order.service.common.valueobject.NotificationType;
import com.commerce.order.service.order.entity.Address;
import com.commerce.order.service.order.entity.Order;
import com.commerce.order.service.order.usecase.OrderNotificationMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

class OrderNotificationKafkaPublisherTest {

    @InjectMocks
    private OrderNotificationKafkaPublisher kafkaPublisher;

    @Mock
    private KafkaProducerWithoutCallback<String, NotificationRequestAvroModel> kafkaProducer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_publish() throws JsonProcessingException {
        //given
        OrderNotificationMessage orderNotificationMessage = buildOrderNotificationMessage();

        //when
        kafkaPublisher.publish(orderNotificationMessage);

        //then
        verify(kafkaProducer).send(any(), any(), any());
    }

    private OrderNotificationMessage buildOrderNotificationMessage() {
        return new OrderNotificationMessage(
                Order.builder()
                        .id(1L)
                        .customerId(1L)
                        .cost(new Money(BigDecimal.TEN))
                        .failureMessages(Collections.EMPTY_LIST)
                        .deliveryAddress(Address.builder()
                                .id(1L)
                                .city("city")
                                .county("county")
                                .neighborhood("neigborhood")
                                .street("street")
                                .postalCode("postalcode")
                                .build())
                        .items(Collections.emptyList())
                        .build(),
                NotificationType.APPROVING,
                "message"
        );
    }
}
