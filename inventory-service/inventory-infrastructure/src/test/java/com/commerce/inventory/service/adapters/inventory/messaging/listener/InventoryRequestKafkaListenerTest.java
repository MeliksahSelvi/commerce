package com.commerce.inventory.service.adapters.inventory.messaging.listener;

import ch.qos.logback.classic.Level;
import com.commerce.inventory.service.adapters.inventory.common.LoggerTest;
import com.commerce.inventory.service.inventory.port.messaging.input.InventoryRequestMessageListener;
import com.commerce.inventory.service.inventory.usecase.InventoryRequest;
import com.commerce.kafka.model.InventoryRequestAvroModel;
import com.commerce.kafka.model.OrderInventoryStatus;
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

    private List<String> keys;
    private List<Integer> partitions;
    private List<Long> offsets;

    public InventoryRequestKafkaListenerTest() {
        super(InventoryRequestKafkaListener.class);
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
    void should_receive_when_order_inventory_status_checking() {
        //given
        var messages = buildMessages(OrderInventoryStatus.CHECKING);
        var logMessage = buildLogMessage(messages, keys, partitions, offsets);

        //when
        kafkaListener.receive(messages, keys, partitions, offsets);

        //then
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
        verify(inventoryListener).checking(any(InventoryRequest.class));
    }

    @Test
    void should_receive_when_order_inventory_status_checking_rollback() {
        //given
        var messages = buildMessages(OrderInventoryStatus.CHECKING_ROLLBACK);
        var logMessage = buildLogMessage(messages, keys, partitions, offsets);

        //when
        kafkaListener.receive(messages, keys, partitions, offsets);

        //then
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
        verify(inventoryListener).checking(any(InventoryRequest.class));
    }

    @Test
    void should_receive_when_order_inventory_status_updating() {
        //given
        var messages = buildMessages(OrderInventoryStatus.UPDATING);
        var logMessage = buildLogMessage(messages, keys, partitions, offsets);

        //when
        kafkaListener.receive(messages, keys, partitions, offsets);

        //then
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
        verify(inventoryListener).updating(any(InventoryRequest.class));
    }

    @Test
    void should_receive_when_order_inventory_status_updating_rollback() {
        //given
        var messages = buildMessages(OrderInventoryStatus.UPDATING_ROLLBACK);
        var logMessage = buildLogMessage(messages, keys, partitions, offsets);

        //when
        kafkaListener.receive(messages, keys, partitions, offsets);

        //then
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
        verify(inventoryListener).updating(any(InventoryRequest.class));
    }

    private List<InventoryRequestAvroModel> buildMessages(OrderInventoryStatus orderInventoryStatus) {
        var avroModel = buildAvroModel(orderInventoryStatus);
        List<InventoryRequestAvroModel> messages = new ArrayList<>();
        messages.add(avroModel);
        return messages;
    }

    private InventoryRequestAvroModel buildAvroModel(OrderInventoryStatus orderInventoryStatus) {
        return InventoryRequestAvroModel.newBuilder()
                .setOrderInventoryStatus(orderInventoryStatus)
                .setSagaId(UUID.randomUUID().toString())
                .setCost(BigDecimal.ONE)
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
