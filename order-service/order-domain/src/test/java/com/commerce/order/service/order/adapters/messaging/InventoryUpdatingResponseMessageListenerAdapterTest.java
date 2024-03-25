package com.commerce.order.service.order.adapters.messaging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.commerce.order.service.order.adapters.messaging.adapter.FakeInventoryUpdatingRollbackSagaStep;
import com.commerce.order.service.common.valueobject.InventoryStatus;
import com.commerce.order.service.common.valueobject.OrderInventoryStatus;
import com.commerce.order.service.order.adapters.messaging.adapter.FakeInventoryUpdatingSagaStep;
import com.commerce.order.service.appender.MemoryApender;
import com.commerce.order.service.order.usecase.InventoryResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

class InventoryUpdatingResponseMessageListenerAdapterTest {

    InventoryUpdatingResponseMessageListenerAdapter adapter;
    MemoryApender memoryApender;

    @BeforeEach
    void setUp() {
        adapter = new InventoryUpdatingResponseMessageListenerAdapter(new FakeInventoryUpdatingSagaStep(), new FakeInventoryUpdatingRollbackSagaStep());

        Logger logger = (Logger) LoggerFactory.getLogger(adapter.getClass());
        memoryApender = new MemoryApender();
        memoryApender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
        logger.setLevel(Level.INFO);
        logger.addAppender(memoryApender);
        memoryApender.start();
    }

    @AfterEach
    void cleanUp() {
        memoryApender.reset();
        memoryApender.stop();
    }

    @Test
    void should_updating_inventory_status_available() {
        //given
        var inventoryResponse = buildInventoryResponseWithParameter(InventoryStatus.AVAILABLE, OrderInventoryStatus.UPDATING);
        var logMessage = "InventoryResponse is available for Inventory updating action";

        //when
        //then
        assertDoesNotThrow(() -> adapter.updating(inventoryResponse));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_updating_inventory_status_non_available() {
        //given
        var inventoryResponse = buildInventoryResponseWithParameter(InventoryStatus.NON_AVAILABLE, OrderInventoryStatus.UPDATING);
        var logMessage = "InventoryResponse is not available for Inventory updating action";

        //when
        //then
        assertDoesNotThrow(() -> adapter.updating(inventoryResponse));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_updating_rollback_inventory_status_available() {
        //given
        var inventoryResponse = buildInventoryResponseWithParameter(InventoryStatus.AVAILABLE, OrderInventoryStatus.UPDATING_ROLLBACK);
        var logMessage = "InventoryResponse is available for Inventory updating rollback action";

        //when
        //then
        assertDoesNotThrow(() -> adapter.updatingRollback(inventoryResponse));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_updating_rollback_inventory_status_non_available() {
        //given
        var inventoryResponse = buildInventoryResponseWithParameter(InventoryStatus.NON_AVAILABLE, OrderInventoryStatus.UPDATING_ROLLBACK);
        var logMessage = "InventoryResponse is not available for Inventory updating rollback action";

        //when
        //then
        assertDoesNotThrow(() -> adapter.updatingRollback(inventoryResponse));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    private InventoryResponse buildInventoryResponseWithParameter(InventoryStatus inventoryStatus, OrderInventoryStatus orderInventoryStatus) {
        return new InventoryResponse(UUID.randomUUID(), 1L, 1L, inventoryStatus, orderInventoryStatus, new ArrayList<>());
    }
}