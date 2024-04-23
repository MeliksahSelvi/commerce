package com.commerce.order.service.adapters.order.messaging.listener;

import ch.qos.logback.classic.Level;
import com.commerce.order.service.adapters.order.common.LoggerTest;
import com.commerce.order.service.adapters.order.messaging.listener.common.ListenerCommonData;
import com.commerce.order.service.common.messaging.kafka.model.InventoryResponseKafkaModel;
import com.commerce.order.service.common.valueobject.InventoryStatus;
import com.commerce.order.service.common.valueobject.OrderInventoryStatus;
import com.commerce.order.service.order.port.json.JsonPort;
import com.commerce.order.service.order.port.messaging.input.InventoryCheckingResponseMessageListener;
import com.commerce.order.service.order.port.messaging.input.InventoryUpdatingResponseMessageListener;
import com.commerce.order.service.order.usecase.InventoryResponse;
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
class InventoryResponseKafkaListenerTest extends LoggerTest<InventoryResponseKafkaListener> {

    @InjectMocks
    private InventoryResponseKafkaListener kafkaListener;

    @Mock
    private InventoryCheckingResponseMessageListener checkingListener;

    @Mock
    private InventoryUpdatingResponseMessageListener updatingListener;

    @Mock
    private JsonPort jsonPort;

    private ListenerCommonData listenerCommonData;
    private ObjectMapper objectMapper;
    private List<String> keys;
    private List<Integer> partitions;
    private List<Long> offsets;

    public InventoryResponseKafkaListenerTest() {
        super(InventoryResponseKafkaListener.class);
    }

    @BeforeEach
    void setUp() {
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        this.listenerCommonData = new ListenerCommonData();
        this.keys = listenerCommonData.buildKeys();
        this.partitions = listenerCommonData.buildPartitions();
        this.offsets = listenerCommonData.buildOffsets();
    }

    @AfterEach
    @Override
    protected void cleanUp() {
        cleanUpActions();
    }

    @Test
    void should_receive_when_order_inventory_status_checking() {
        //given
        var messages = buildMessages(OrderInventoryStatus.CHECKING);
        var logMessage = listenerCommonData.buildLogMessage(messages, keys, partitions, offsets);
        List<String> messagesAsStr = convertKafkaModelListToJsonList(messages);
        when(jsonPort.exractDataFromJson(messagesAsStr.get(0), InventoryResponseKafkaModel.class)).thenReturn(messages.get(0));

        //when
        kafkaListener.receive(messagesAsStr, keys, partitions, offsets);

        //then
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
        verify(checkingListener).checking(any(InventoryResponse.class));
    }

    @Test
    void should_receive_when_order_inventory_status_checking_rollback() {
        //given
        var messages = buildMessages(OrderInventoryStatus.CHECKING_ROLLBACK);
        var logMessage = listenerCommonData.buildLogMessage(messages, keys, partitions, offsets);
        List<String> messagesAsStr = convertKafkaModelListToJsonList(messages);
        when(jsonPort.exractDataFromJson(messagesAsStr.get(0), InventoryResponseKafkaModel.class)).thenReturn(messages.get(0));

        //when
        kafkaListener.receive(messagesAsStr, keys, partitions, offsets);

        //then
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
        verify(checkingListener).checkingRollback(any(InventoryResponse.class));
    }

    @Test
    void should_receive_when_order_inventory_status_updating() {
        //given
        var messages = buildMessages(OrderInventoryStatus.UPDATING);
        var logMessage = listenerCommonData.buildLogMessage(messages, keys, partitions, offsets);
        List<String> messagesAsStr = convertKafkaModelListToJsonList(messages);
        when(jsonPort.exractDataFromJson(messagesAsStr.get(0), InventoryResponseKafkaModel.class)).thenReturn(messages.get(0));

        //when
        kafkaListener.receive(messagesAsStr, keys, partitions, offsets);

        //then
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
        verify(updatingListener).updating(any(InventoryResponse.class));
    }

    @Test
    void should_receive_when_order_inventory_status_updating_rollback() {
        //given
        var messages = buildMessages(OrderInventoryStatus.UPDATING_ROLLBACK);
        var logMessage = listenerCommonData.buildLogMessage(messages, keys, partitions, offsets);
        List<String> messagesAsStr = convertKafkaModelListToJsonList(messages);
        when(jsonPort.exractDataFromJson(messagesAsStr.get(0), InventoryResponseKafkaModel.class)).thenReturn(messages.get(0));

        //when
        kafkaListener.receive(messagesAsStr, keys, partitions, offsets);

        //then
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
        verify(updatingListener).updatingRollback(any(InventoryResponse.class));
    }

    private List<InventoryResponseKafkaModel> buildMessages(OrderInventoryStatus orderInventoryStatus) {
        var kafkaModel = buildKafkaModel(orderInventoryStatus);
        List<InventoryResponseKafkaModel> messages = new ArrayList<>();
        messages.add(kafkaModel);
        return messages;
    }

    private InventoryResponseKafkaModel buildKafkaModel(OrderInventoryStatus orderInventoryStatus) {
        return new InventoryResponseKafkaModel(UUID.randomUUID().toString(), 1L, 1L, InventoryStatus.AVAILABLE, orderInventoryStatus, new ArrayList<>());
    }

    private List<String> convertKafkaModelListToJsonList(List<InventoryResponseKafkaModel> kafkaModelList) {
        return kafkaModelList.stream().map(kafkaModel -> {
            try {
                return objectMapper.writeValueAsString(kafkaModel);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }).toList();
    }
}
