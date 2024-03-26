package com.commerce.inventory.service.adapters.inventory.messaging.publisher;

import com.commerce.inventory.service.common.messaging.kafka.helper.KafkaHelper;
import com.commerce.inventory.service.common.outbox.OutboxStatus;
import com.commerce.inventory.service.common.valueobject.InventoryStatus;
import com.commerce.inventory.service.common.valueobject.OrderInventoryStatus;
import com.commerce.inventory.service.outbox.entity.OrderOutbox;
import com.commerce.inventory.service.outbox.entity.OrderOutboxPayload;
import com.commerce.kafka.model.InventoryResponseAvroModel;
import com.commerce.kafka.producer.KafkaProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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

class InventoryResponseKafkaPublisherTest {

    @InjectMocks
    private InventoryResponseKafkaPublisher kafkaPublisher;

    @Mock
    private KafkaProducer<String, InventoryResponseAvroModel> kafkaProducer;

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

        InventoryResponseAvroModel avroModel = buildAvroModel();

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
        return new OrderOutboxPayload(1L, 1L, OrderInventoryStatus.CHECKING, InventoryStatus.AVAILABLE, Collections.emptyList());
    }

    private OrderOutbox buildInventoryOutbox(UUID sagaId, OrderOutboxPayload payload) throws JsonProcessingException {
        return OrderOutbox.builder()
                .id(1L)
                .sagaId(sagaId)
                .payload(objectMapper.writeValueAsString(payload))
                .outboxStatus(OutboxStatus.STARTED)
                .build();
    }

    private InventoryResponseAvroModel buildAvroModel() {
        return InventoryResponseAvroModel.newBuilder()
                .setFailureMessages(Collections.EMPTY_LIST)
                .setInventoryStatus(com.commerce.kafka.model.InventoryStatus.AVAILABLE)
                .setOrderInventoryStatus(com.commerce.kafka.model.OrderInventoryStatus.CHECKING)
                .setSagaId(UUID.randomUUID().toString())
                .setOrderId(1L)
                .setCustomerId(1L)
                .build();
    }
}
