package com.commerce.shipping.service.adapters.shipping.messaging.publisher;

import com.commerce.shipping.service.common.messaging.kafka.model.NotificationRequestKafkaModel;
import com.commerce.shipping.service.common.messaging.kafka.producer.KafkaProducerWithoutCallback;
import com.commerce.shipping.service.common.valueobject.DeliveryStatus;
import com.commerce.shipping.service.common.valueobject.NotificationType;
import com.commerce.shipping.service.shipping.model.Address;
import com.commerce.shipping.service.shipping.model.Shipping;
import com.commerce.shipping.service.shipping.usecase.OrderNotificationMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

class OrderNotificationKafkaPublisherTest {

    @InjectMocks
    private OrderNotificationKafkaPublisher kafkaPublisher;

    @Mock
    private KafkaProducerWithoutCallback kafkaProducer;

    @Mock
    private ObjectMapper mockObjectMapper;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    void should_publish() throws JsonProcessingException {
        //given
        OrderNotificationMessage orderNotificationMessage = buildOrderNotificationMessage();
        NotificationRequestKafkaModel kafkaModel=new NotificationRequestKafkaModel(orderNotificationMessage);
        when(mockObjectMapper.writeValueAsString(kafkaModel)).thenReturn(objectMapper.writeValueAsString(kafkaModel));

        //when
        kafkaPublisher.publish(orderNotificationMessage);

        //then
        verify(kafkaProducer).send(any(), any(), any());
    }

    private OrderNotificationMessage buildOrderNotificationMessage() {
        return new OrderNotificationMessage(
                Shipping.builder()
                        .id(1L)
                        .orderId(1L)
                        .customerId(1L)
                        .address(Address.builder()
                                .id(1L)
                                .city("city")
                                .county("county")
                                .neighborhood("neigborhood")
                                .street("street")
                                .postalCode("postalcode")
                                .build()
                        )
                        .deliveryStatus(DeliveryStatus.APPROVED)
                        .items(Collections.emptyList())
                        .build(),
                NotificationType.APPROVING,
                "message"
        );
    }
}
