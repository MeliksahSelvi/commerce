package com.commerce.order.service.adapters.order.messaging.publisher;

import com.commerce.order.service.common.messaging.kafka.helper.KafkaHelper;
import com.commerce.order.service.common.messaging.kafka.model.InventoryRequestKafkaModel;
import com.commerce.order.service.common.messaging.kafka.producer.KafkaProducer;
import com.commerce.order.service.common.outbox.OutboxStatus;
import com.commerce.order.service.common.saga.SagaStatus;
import com.commerce.order.service.common.valueobject.OrderInventoryStatus;
import com.commerce.order.service.common.valueobject.OrderStatus;
import com.commerce.order.service.order.port.json.JsonPort;
import com.commerce.order.service.outbox.model.InventoryOutbox;
import com.commerce.order.service.outbox.model.InventoryOutboxPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
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

class InventoryRequestKafkaPublisherTest {

    @InjectMocks
    private InventoryRequestKafkaPublisher kafkaPublisher;

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
        InventoryOutboxPayload payload = buildPayload();

        InventoryOutbox inventoryOutbox = buildInventoryOutbox(sagaId, payload);

        BiConsumer<InventoryOutbox, OutboxStatus> outboxCallback = (result, ex) -> {
        };

        InventoryRequestKafkaModel kafkaModel = buildKafkaModel();

        when(kafkaHelper.getPayload(inventoryOutbox.getPayload(), InventoryOutboxPayload.class)).thenReturn(payload);
        when(kafkaHelper.getKafkaCallback(kafkaModel, inventoryOutbox, outboxCallback, 1L)).thenReturn((result, ex) -> {
        });
        when(jsonPort.convertDataToJson(kafkaModel)).thenReturn(objectMapper.writeValueAsString(kafkaModel));

        //when
        kafkaPublisher.publish(inventoryOutbox, outboxCallback);

        //then
        verify(kafkaHelper).getPayload(inventoryOutbox.getPayload(), InventoryOutboxPayload.class);
        verify(kafkaHelper).getKafkaCallback(any(), any(), any(), any());
        verify(kafkaProducer).send(any(), any(), any(), any());
    }

    private InventoryOutboxPayload buildPayload() {
        return new InventoryOutboxPayload(1L, 1L, BigDecimal.TEN, Collections.emptyList(), OrderInventoryStatus.CHECKING);
    }

    private InventoryOutbox buildInventoryOutbox(UUID sagaId, InventoryOutboxPayload payload) throws JsonProcessingException {
        return InventoryOutbox.builder()
                .id(1L)
                .sagaStatus(SagaStatus.CHECKING)
                .sagaId(sagaId)
                .payload(objectMapper.writeValueAsString(payload))
                .outboxStatus(OutboxStatus.STARTED)
                .orderInventoryStatus(OrderInventoryStatus.CHECKING)
                .orderStatus(OrderStatus.CHECKING)
                .build();
    }

    private InventoryRequestKafkaModel buildKafkaModel() {
        return new InventoryRequestKafkaModel(UUID.randomUUID().toString(), 1L, 1L, BigDecimal.ONE, OrderInventoryStatus.CHECKING, new ArrayList<>());
    }
}
