package com.commerce.order.service.adapters.order.messaging.publisher;

import com.commerce.order.service.common.messaging.kafka.helper.KafkaHelper;
import com.commerce.order.service.common.messaging.kafka.model.PaymentRequestKafkaModel;
import com.commerce.order.service.common.messaging.kafka.producer.KafkaProducer;
import com.commerce.order.service.common.outbox.OutboxStatus;
import com.commerce.order.service.common.saga.SagaStatus;
import com.commerce.order.service.common.valueobject.OrderPaymentStatus;
import com.commerce.order.service.common.valueobject.OrderStatus;
import com.commerce.order.service.order.port.json.JsonPort;
import com.commerce.order.service.outbox.model.PaymentOutbox;
import com.commerce.order.service.outbox.model.PaymentOutboxPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.function.BiConsumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

class PaymentRequestKafkaPublisherTest {

    @InjectMocks
    private PaymentRequestKafkaPublisher kafkaPublisher;

    @Mock
    private KafkaProducer kafkaProducer;

    @Mock
    private KafkaHelper kafkaHelper;

    @Mock
    private JsonPort jsonPort;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    void should_publish() throws JsonProcessingException {
        //given
        String inventoryRequestTopicName = "topic";
        UUID sagaId = UUID.randomUUID();
        PaymentOutboxPayload payload = buildPayload(sagaId);

        PaymentOutbox paymentOutbox = buildInventoryOutbox(sagaId, payload);

        BiConsumer<PaymentOutbox, OutboxStatus> outboxCallback = (result, ex) -> {
        };

        PaymentRequestKafkaModel kafkaModel = buildKafkaModel();

        when(kafkaHelper.getPayload(paymentOutbox.getPayload(), PaymentOutboxPayload.class)).thenReturn(payload);
        when(kafkaHelper.getKafkaCallback(kafkaModel, paymentOutbox, outboxCallback, 1L)).thenReturn((result, ex) -> {
        });
        when(jsonPort.convertDataToJson(kafkaModel)).thenReturn(objectMapper.writeValueAsString(kafkaModel));

        //when
        kafkaPublisher.publish(paymentOutbox, outboxCallback);

        //then
        verify(kafkaHelper).getPayload(paymentOutbox.getPayload(), PaymentOutboxPayload.class);
        verify(kafkaHelper).getKafkaCallback(any(), any(), any(), any());
        verify(kafkaProducer).send(any(), any(), any(), any());
    }

    private PaymentOutbox buildInventoryOutbox(UUID sagaId, PaymentOutboxPayload payload) throws JsonProcessingException {
        return PaymentOutbox.builder()
                .id(1L)
                .sagaStatus(SagaStatus.CHECKING)
                .sagaId(sagaId)
                .payload(objectMapper.writeValueAsString(payload))
                .outboxStatus(OutboxStatus.STARTED)
                .orderStatus(OrderStatus.CHECKING)
                .build();
    }

    private PaymentOutboxPayload buildPayload(UUID sagaId) {
        return new PaymentOutboxPayload(sagaId, 1L, 1L, BigDecimal.TEN, com.commerce.order.service.common.valueobject.OrderPaymentStatus.PENDING);
    }

    private PaymentRequestKafkaModel buildKafkaModel() {
        return new PaymentRequestKafkaModel(UUID.randomUUID().toString(), 1L, 1L, BigDecimal.ONE, OrderPaymentStatus.PENDING);
    }
}
