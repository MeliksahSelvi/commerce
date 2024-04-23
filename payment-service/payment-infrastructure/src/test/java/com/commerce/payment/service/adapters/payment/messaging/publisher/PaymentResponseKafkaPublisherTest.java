package com.commerce.payment.service.adapters.payment.messaging.publisher;

import com.commerce.payment.service.common.messaging.kafka.helper.KafkaHelper;
import com.commerce.payment.service.common.messaging.kafka.model.PaymentResponseKafkaModel;
import com.commerce.payment.service.common.messaging.kafka.producer.KafkaProducer;
import com.commerce.payment.service.common.outbox.OutboxStatus;
import com.commerce.payment.service.common.valueobject.PaymentStatus;
import com.commerce.payment.service.outbox.entity.OrderOutbox;
import com.commerce.payment.service.outbox.entity.OrderOutboxPayload;
import com.commerce.payment.service.payment.port.json.JsonPort;
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
        UUID sagaId = UUID.randomUUID();
        OrderOutboxPayload payload = buildPayload();

        OrderOutbox orderOutbox = buildInventoryOutbox(sagaId, payload);

        BiConsumer<OrderOutbox, OutboxStatus> outboxCallback = (result, ex) -> {
        };

        PaymentResponseKafkaModel kafkaModel = buildKafkaModel();

        when(kafkaHelper.getPayload(orderOutbox.getPayload(), OrderOutboxPayload.class)).thenReturn(payload);
        when(kafkaHelper.getKafkaCallback(kafkaModel, orderOutbox, outboxCallback, 1L)).thenReturn((result, ex) -> {
        });
        when(jsonPort.convertDataToJson(kafkaModel)).thenReturn(objectMapper.writeValueAsString(kafkaModel));

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

    private PaymentResponseKafkaModel buildKafkaModel() {
        return new PaymentResponseKafkaModel(UUID.randomUUID().toString(), 1L, 1L, 1L, BigDecimal.TEN,
                PaymentStatus.COMPLETED, Collections.EMPTY_LIST);
    }
}
