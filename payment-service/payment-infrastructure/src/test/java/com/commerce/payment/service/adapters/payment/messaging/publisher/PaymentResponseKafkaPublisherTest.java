package com.commerce.payment.service.adapters.payment.messaging.publisher;

import com.commerce.kafka.model.InventoryResponseAvroModel;
import com.commerce.kafka.model.PaymentResponseAvroModel;
import com.commerce.kafka.model.PaymentStatus;
import com.commerce.kafka.producer.KafkaProducer;
import com.commerce.payment.service.common.messaging.kafka.helper.KafkaHelper;
import com.commerce.payment.service.common.outbox.OutboxStatus;
import com.commerce.payment.service.outbox.entity.OrderOutbox;
import com.commerce.payment.service.outbox.entity.OrderOutboxPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;
import java.util.function.BiConsumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

class PaymentResponseKafkaPublisherTest {

    @InjectMocks
    private PaymentResponseKafkaPublisher kafkaPublisher;

    @Mock
    private KafkaProducer<String, PaymentResponseAvroModel> kafkaProducer;

    @Mock
    private KafkaHelper kafkaHelper;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    void should_publish() throws JsonProcessingException {
        //given
        UUID sagaId = UUID.randomUUID();
        OrderOutboxPayload payload = buildPayload();

        OrderOutbox orderOutbox = buildInventoryOutbox(sagaId, payload);

        BiConsumer<OrderOutbox, OutboxStatus> outboxCallback = (result, ex) -> {
        };

        PaymentResponseAvroModel avroModel = buildAvroModel();

        when(kafkaHelper.getPayload(orderOutbox.getPayload(), OrderOutboxPayload.class)).thenReturn(payload);
        when(kafkaHelper.getKafkaCallback(avroModel, orderOutbox, outboxCallback, 1L)).thenReturn((result, ex) -> {
        });

        //when
        kafkaPublisher.publish(orderOutbox, outboxCallback);

        //then
        verify(kafkaHelper).getPayload(orderOutbox.getPayload(), OrderOutboxPayload.class);
        verify(kafkaHelper).getKafkaCallback(any(), any(), any(), any());
        verify(kafkaProducer).send(any(), any(), any(), any());
    }

    private OrderOutboxPayload buildPayload() {
        return new OrderOutboxPayload(1L, 1L, 1L,
                BigDecimal.TEN, com.commerce.payment.service.common.valueobject.PaymentStatus.COMPLETED, Collections.emptyList());
    }

    private OrderOutbox buildInventoryOutbox(UUID sagaId, OrderOutboxPayload payload) throws JsonProcessingException {
        return OrderOutbox.builder()
                .id(1L)
                .sagaId(sagaId)
                .payload(objectMapper.writeValueAsString(payload))
                .outboxStatus(OutboxStatus.STARTED)
                .build();
    }

    private PaymentResponseAvroModel buildAvroModel() {
        return PaymentResponseAvroModel.newBuilder()
                .setPaymentId(1L)
                .setPaymentStatus(PaymentStatus.COMPLETED)
                .setCost(BigDecimal.TEN)
                .setFailureMessages(Collections.EMPTY_LIST)
                .setSagaId(UUID.randomUUID().toString())
                .setOrderId(1L)
                .setCustomerId(1L)
                .build();
    }
}
