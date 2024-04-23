package com.commerce.notification.service.adapters.notification.messaging.listener;

import ch.qos.logback.classic.Level;
import com.commerce.notification.service.adapters.notification.common.LoggerTest;
import com.commerce.notification.service.common.messaging.kafka.model.AddressKafkaModel;
import com.commerce.notification.service.common.messaging.kafka.model.NotificationRequestKafkaModel;
import com.commerce.notification.service.common.valueobject.NotificationType;
import com.commerce.notification.service.notification.port.messaging.input.OrderNotificationMessageListener;
import com.commerce.notification.service.notification.usecase.OrderNotificationMessage;
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
 * @Created 21.03.2024
 */

@ExtendWith(MockitoExtension.class)
class OrderNotificationKafkaListenerTest extends LoggerTest<OrderNotificationKafkaListener> {

    @InjectMocks
    private OrderNotificationKafkaListener kafkaListener;

    @Mock
    private OrderNotificationMessageListener orderListener;

    @Mock
    private ObjectMapper mockObjectMapper;

    private ObjectMapper objectMapper;
    private List<String> keys;
    private List<Integer> partitions;
    private List<Long> offsets;

    public OrderNotificationKafkaListenerTest() {
        super(OrderNotificationKafkaListener.class);
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
        when(mockObjectMapper.readValue(messagesAsStr.get(0), NotificationRequestKafkaModel.class)).thenReturn(messages.get(0));

        //when
        kafkaListener.receive(messagesAsStr, keys, partitions, offsets);

        //then
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
        verify(orderListener).processMessage(any(OrderNotificationMessage.class));
    }

    private List<NotificationRequestKafkaModel> buildMessages() {
        var kafkaModel = buildKafkaModel();
        List<NotificationRequestKafkaModel> messages = new ArrayList<>();
        messages.add(kafkaModel);
        return messages;
    }

    private NotificationRequestKafkaModel buildKafkaModel() {
        return new NotificationRequestKafkaModel(1L, 1L, "message",
                new AddressKafkaModel(1L, "city", "county", "neigborhood", "street", "postalCode"),
                NotificationType.APPROVING, new ArrayList<>());
    }

    private String buildLogMessage(List<NotificationRequestKafkaModel> messages, List<String> keys, List<Integer> partitions, List<Long> offsets) {
        return String.format("%d number of messages received with keys:[%s], partitions:[%d] and offsets:[%d]"
                , messages.size(), keys.get(0), partitions.get(0), offsets.get(0));
    }

    private List<String> convertKafkaModelListToJsonList(List<NotificationRequestKafkaModel> kafkaModelList) {
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
