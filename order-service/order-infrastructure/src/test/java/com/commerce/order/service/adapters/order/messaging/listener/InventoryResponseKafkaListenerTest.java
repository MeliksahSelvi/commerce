package com.commerce.order.service.adapters.order.messaging.listener;

import ch.qos.logback.classic.Level;
import com.commerce.kafka.model.InventoryResponseAvroModel;
import com.commerce.kafka.model.InventoryStatus;
import com.commerce.kafka.model.OrderInventoryStatus;
import com.commerce.order.service.adapters.order.common.LoggerTest;
import com.commerce.order.service.adapters.order.messaging.listener.common.ListenerCommonData;
import com.commerce.order.service.order.port.messaging.input.InventoryCheckingResponseMessageListener;
import com.commerce.order.service.order.port.messaging.input.InventoryUpdatingResponseMessageListener;
import com.commerce.order.service.order.usecase.InventoryResponse;
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

    private ListenerCommonData listenerCommonData;
    private List<String> keys;
    private List<Integer> partitions;
    private List<Long> offsets;

    public InventoryResponseKafkaListenerTest() {
        super(InventoryResponseKafkaListener.class);
    }

    @BeforeEach
    void setUp() {
        listenerCommonData = new ListenerCommonData();
        keys = listenerCommonData.buildKeys();
        partitions = listenerCommonData.buildPartitions();
        offsets = listenerCommonData.buildOffsets();
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

        //when
        kafkaListener.receive(messages, keys, partitions, offsets);

        //then
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
        verify(checkingListener).checking(any(InventoryResponse.class));
    }

    @Test
    void should_receive_when_order_inventory_status_checking_rollback() {
        //given
        var messages = buildMessages(OrderInventoryStatus.CHECKING_ROLLBACK);
        var logMessage = listenerCommonData.buildLogMessage(messages, keys, partitions, offsets);

        //when
        kafkaListener.receive(messages, keys, partitions, offsets);

        //then
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
        verify(checkingListener).checkingRollback(any(InventoryResponse.class));
    }

    @Test
    void should_receive_when_order_inventory_status_updating() {
        //given
        var messages = buildMessages(OrderInventoryStatus.UPDATING);
        var logMessage = listenerCommonData.buildLogMessage(messages, keys, partitions, offsets);

        //when
        kafkaListener.receive(messages, keys, partitions, offsets);

        //then
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
        verify(updatingListener).updating(any(InventoryResponse.class));
    }

    @Test
    void should_receive_when_order_inventory_status_updating_rollback() {
        //given
        var messages = buildMessages(OrderInventoryStatus.UPDATING_ROLLBACK);
        var logMessage = listenerCommonData.buildLogMessage(messages, keys, partitions, offsets);

        //when
        kafkaListener.receive(messages, keys, partitions, offsets);

        //then
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
        verify(updatingListener).updatingRollback(any(InventoryResponse.class));
    }

    private List<InventoryResponseAvroModel> buildMessages(OrderInventoryStatus orderInventoryStatus) {
        var avroModel = buildAvroModel(orderInventoryStatus);
        List<InventoryResponseAvroModel> messages = new ArrayList<>();
        messages.add(avroModel);
        return messages;
    }

    private InventoryResponseAvroModel buildAvroModel(OrderInventoryStatus orderInventoryStatus) {
        return InventoryResponseAvroModel.newBuilder()
                .setSagaId(UUID.randomUUID().toString())
                .setOrderId(1L)
                .setCustomerId(1L)
                .setOrderInventoryStatus(orderInventoryStatus)
                .setInventoryStatus(InventoryStatus.AVAILABLE)
                .setFailureMessages(new ArrayList<>())
                .build();
    }
}
