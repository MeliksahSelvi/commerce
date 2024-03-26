package com.commerce.shipping.service.adapters.shipping.messaging.listener;

import ch.qos.logback.classic.Level;
import com.commerce.kafka.model.AddressPayload;
import com.commerce.kafka.model.NotificationRequestAvroModel;
import com.commerce.kafka.model.NotificationType;
import com.commerce.shipping.service.adapters.shipping.common.LoggerTest;
import com.commerce.shipping.service.shipping.port.messaging.input.OrderNotificationMessageListener;
import com.commerce.shipping.service.shipping.usecase.OrderNotificationMessage;
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
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

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

    private List<String> keys;
    private List<Integer> partitions;
    private List<Long> offsets;

    public OrderNotificationKafkaListenerTest() {
        super(OrderNotificationKafkaListener.class);
    }

    @BeforeEach
    void setUp() {
        keys = buildKeys();
        partitions = buildPartitions();
        offsets = buildOffsets();
    }

    @AfterEach
    @Override
    protected void cleanUp() {
        cleanUpActions();
    }

    @Test
    void should_receive_when_notification_type_approving() {
        //given
        var messages = buildMessages(NotificationType.APPROVING);
        var logMessage = buildLogMessage(messages, keys, partitions, offsets);

        //when
        kafkaListener.receive(messages, keys, partitions, offsets);

        //then
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
        verify(orderListener).approving(any(OrderNotificationMessage.class));
    }

    @Test
    void should_receive_when_notification_type_cancelling() {
        //given
        var messages = buildMessages(NotificationType.CANCELLING);
        var logMessage = buildLogMessage(messages, keys, partitions, offsets);

        //when
        kafkaListener.receive(messages, keys, partitions, offsets);

        //then
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
        verify(orderListener).cancelling(any(OrderNotificationMessage.class));
    }

    @Test
    void should_receive_fail_when_notification_type_delivering() {
        //given
        var messages = buildMessages(NotificationType.DELIVERING);
        var logMessage = buildLogMessage(messages, keys, partitions, offsets);

        //when
        kafkaListener.receive(messages, keys, partitions, offsets);

        //then
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
        verify(orderListener, never()).approving(any(OrderNotificationMessage.class));
        verify(orderListener, never()).cancelling(any(OrderNotificationMessage.class));
    }

    @Test
    void should_receive_fail_when_notification_type_shipping() {
        //given
        var messages = buildMessages(NotificationType.SHIPPING);
        var logMessage = buildLogMessage(messages, keys, partitions, offsets);

        //when
        kafkaListener.receive(messages, keys, partitions, offsets);

        //then
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
        verify(orderListener, never()).approving(any(OrderNotificationMessage.class));
        verify(orderListener, never()).cancelling(any(OrderNotificationMessage.class));
    }

    private List<NotificationRequestAvroModel> buildMessages(NotificationType notificationType) {
        var avroModel = buildAvroModel(notificationType);
        List<NotificationRequestAvroModel> messages = new ArrayList<>();
        messages.add(avroModel);
        return messages;
    }

    private NotificationRequestAvroModel buildAvroModel(NotificationType notificationType) {
        return NotificationRequestAvroModel.newBuilder()
                .setAddressPayload(new AddressPayload())
                .setMessage("message")
                .setNotificationType(notificationType)
                .setOrderId(1L)
                .setCustomerId(1L)
                .setItems(new ArrayList<>())
                .build();
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

    private String buildLogMessage(List messages, List<String> keys, List<Integer> partitions, List<Long> offsets) {
        return String.format("%d number of messages received with keys:[%s], partitions:[%d] and offsets:[%d]"
                , messages.size(), keys.get(0), partitions.get(0), offsets.get(0));
    }
}
