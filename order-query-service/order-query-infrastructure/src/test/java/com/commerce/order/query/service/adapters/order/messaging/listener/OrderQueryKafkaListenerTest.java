package com.commerce.order.query.service.adapters.order.messaging.listener;

import ch.qos.logback.classic.Level;
import com.commerce.order.query.service.adapters.order.common.LoggerTest;
import com.commerce.order.query.service.common.messaging.kafka.model.OrderQueryKafkaModel;
import com.commerce.order.query.service.common.valueobject.OrderStatus;
import com.commerce.order.query.service.order.model.OrderQuery;
import com.commerce.order.query.service.order.port.messaging.input.OrderQueryMessageListener;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @Author mselvi
 * @Created 27.04.2024
 */

@ExtendWith(MockitoExtension.class)
class OrderQueryKafkaListenerTest extends LoggerTest<OrderQueryKafkaListener> {

    @InjectMocks
    private OrderQueryKafkaListener kafkaListener;

    @Mock
    private OrderQueryMessageListener messageListener;

    @Mock
    private ObjectMapper mockObjectMapper;

    private ObjectMapper objectMapper;
    private List<String> keys;
    private List<Integer> partitions;
    private List<Long> offsets;

    public OrderQueryKafkaListenerTest() {
        super(OrderQueryKafkaListener.class);
    }

    @BeforeEach
    void setUp() {
        this.objectMapper = new ObjectMapper();
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
    void should_receive() throws JsonProcessingException {
        //given
        var messages = buildMessages();
        var logMessage = buildLogMessage(messages, keys, partitions, offsets);
        List<String> messagesAsStr = convertKafkaModelListToJsonList(messages);
        when(mockObjectMapper.readValue(messagesAsStr.get(0), OrderQueryKafkaModel.class)).thenReturn(messages.get(0));

        //when
        kafkaListener.receive(messagesAsStr, keys, partitions, offsets);

        //then
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
        verify(messageListener).processMessage(any(OrderQuery.class));
    }

    private List<OrderQueryKafkaModel> buildMessages() {
        var kafkaModel = buildKafkaModel();
        List<OrderQueryKafkaModel> messages = new ArrayList<>();
        messages.add(kafkaModel);
        return messages;
    }

    private OrderQueryKafkaModel buildKafkaModel() {
        return new OrderQueryKafkaModel(1L, OrderStatus.PENDING);
    }

    private String buildLogMessage(List<OrderQueryKafkaModel> messages, List<String> keys, List<Integer> partitions, List<Long> offsets) {
        return String.format("%d number of messages received with keys:[%s], partitions:[%d] and offsets:[%d]"
                , messages.size(), keys.get(0), partitions.get(0), offsets.get(0));
    }

    private List<String> convertKafkaModelListToJsonList(List<OrderQueryKafkaModel> kafkaModelList) {
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
