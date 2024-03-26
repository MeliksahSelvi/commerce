package com.commerce.shipping.service.adapters.shipping.messaging.publisher;

import com.commerce.kafka.model.NotificationRequestAvroModel;
import com.commerce.kafka.producer.KafkaProducerWithoutCallback;
import com.commerce.shipping.service.common.valueobject.DeliveryStatus;
import com.commerce.shipping.service.common.valueobject.NotificationType;
import com.commerce.shipping.service.shipping.entity.Address;
import com.commerce.shipping.service.shipping.entity.Shipping;
import com.commerce.shipping.service.shipping.usecase.OrderNotificationMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
