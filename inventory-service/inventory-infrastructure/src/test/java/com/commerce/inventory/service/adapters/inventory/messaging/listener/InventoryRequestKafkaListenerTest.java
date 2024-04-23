package com.commerce.inventory.service.adapters.inventory.messaging.listener;

import ch.qos.logback.classic.Level;
import com.commerce.inventory.service.adapters.inventory.common.LoggerTest;
import com.commerce.inventory.service.common.messaging.kafka.model.InventoryRequestKafkaModel;
import com.commerce.inventory.service.common.valueobject.OrderInventoryStatus;
import com.commerce.inventory.service.inventory.port.json.JsonPort;
import com.commerce.inventory.service.inventory.port.messaging.input.InventoryRequestMessageListener;
import com.commerce.inventory.service.inventory.usecase.InventoryRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

@ExtendWith(MockitoExtension.class)
class InventoryRequestKafkaListenerTest extends LoggerTest<InventoryRequestKafkaListener> {

    @InjectMocks
    private InventoryRequestKafkaListener kafkaListener;

    @Mock
    private InventoryRequestMessageListener inventoryListener;

    @Mock
    private JsonPort jsonPort;

    private ObjectMapper objectMapper;
    private List<String> keys;
    private List<Integer> partitions;
    private List<Long> offsets;

    public InventoryRequestKafkaListenerTest() {
        super(InventoryRequestKafkaListener.class);
    }

    @BeforeEach
    void setUp() {
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        this.keys = buildKeys();
        this.partitions = buildPartitions();
        this.offsets = buildOffsets();
    }

    @AfterEach
    @Override
    protected void cleanUp() {
        cleanUpActions();
    }

    @Test
    void should_receive_when_order_inventory_status_checking() throws JsonProcessingException {
        //given
        var messages = buildMessages(OrderInventoryStatus.CHECKING);
        var logMessage = buildLogMessage(messages, keys, partitions, offsets);
        List<String> messagesAsStr = convertKafkaModelListToJsonList(messages);
        when(jsonPort.exractDataFromJson(messagesAsStr.get(0), InventoryRequestKafkaModel.class)).thenReturn(messages.get(0));

        //when
        kafkaListener.receive(messagesAsStr, keys, partitions, offsets);

        //then
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
        verify(inventoryListener).checking(any(InventoryRequest.class));
    }

    @Test
    void should_receive_when_order_inventory_status_checking_rollback() throws JsonProcessingException {
        //given
        var messages = buildMessages(OrderInventoryStatus.CHECKING_ROLLBACK);
        var logMessage = buildLogMessage(messages, keys, partitions, offsets);
        List<String> messagesAsStr = convertKafkaModelListToJsonList(messages);
        when(jsonPort.exractDataFromJson(messagesAsStr.get(0), InventoryRequestKafkaModel.class)).thenReturn(messages.get(0));

        //when
        kafkaListener.receive(messagesAsStr, keys, partitions, offsets);

        //then
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
        verify(inventoryListener).checking(any(InventoryRequest.class));
    }

    @Test
    void should_receive_when_order_inventory_status_updating() throws JsonProcessingException {
        //given
        var messages = buildMessages(OrderInventoryStatus.UPDATING);
        var logMessage = buildLogMessage(messages, keys, partitions, offsets);
        List<String> messagesAsStr = convertKafkaModelListToJsonList(messages);
        when(jsonPort.exractDataFromJson(messagesAsStr.get(0), InventoryRequestKafkaModel.class)).thenReturn(messages.get(0));

        //when
        kafkaListener.receive(messagesAsStr, keys, partitions, offsets);

        //then
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
        verify(inventoryListener).updating(any(InventoryRequest.class));
    }

    @Test
    void should_receive_when_order_inventory_status_updating_rollback() throws JsonProcessingException {
        //given
        var messages = buildMessages(OrderInventoryStatus.UPDATING_ROLLBACK);
        var logMessage = buildLogMessage(messages, keys, partitions, offsets);
        List<String> messagesAsStr = convertKafkaModelListToJsonList(messages);
        when(jsonPort.exractDataFromJson(messagesAsStr.get(0), InventoryRequestKafkaModel.class)).thenReturn(messages.get(0));

        //when
        kafkaListener.receive(messagesAsStr, keys, partitions, offsets);

        //then
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
        verify(inventoryListener).updating(any(InventoryRequest.class));
    }

    private List<InventoryRequestKafkaModel> buildMessages(OrderInventoryStatus orderInventoryStatus) {
        var kafkaModel = buildAvroModel(orderInventoryStatus);
        List<InventoryRequestKafkaModel> messages = new ArrayList<>();
        messages.add(kafkaModel);
        return messages;
    }

    private InventoryRequestKafkaModel buildAvroModel(OrderInventoryStatus orderInventoryStatus) {
        return new InventoryRequestKafkaModel(UUID.randomUUID().toString(), 1L, 1L, BigDecimal.ONE, orderInventoryStatus, new ArrayList<>());
    }

    private String buildLogMessage(List<InventoryRequestKafkaModel> messages, List<String> keys, List<Integer> partitions, List<Long> offsets) {
        return String.format("%d number of messages received with keys:[%s], partitions:[%d] and offsets:[%d]"
                , messages.size(), keys.get(0), partitions.get(0), offsets.get(0));
    }

    private List<String> convertKafkaModelListToJsonList(List<InventoryRequestKafkaModel> kafkaModelList) {
        return kafkaModelList.stream().map(kafkaModel -> {
            try {
                return objectMapper.writeValueAsString(kafkaModel);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }).toList();
    }

    private List<String> buildKeys() {
        List<String> keys = new ArrayList<>();
        keys.add("key1");
        return keys;
    }

    private List<Integer> buildPartitions() {
        List<Integer> partitions = new ArrayList<>();
        partitions.add(0);
        return partitions;
    }

    private List<Long> buildOffsets() {
        List<Long> offsets = new ArrayList<>();
        offsets.add(0L);
        return offsets;
    }


}
