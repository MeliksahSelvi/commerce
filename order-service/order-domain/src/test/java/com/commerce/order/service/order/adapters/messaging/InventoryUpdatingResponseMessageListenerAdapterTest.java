package com.commerce.order.service.order.adapters.messaging;

import ch.qos.logback.classic.Level;
import com.commerce.order.service.adapter.saga.FakeInventoryUpdatingRollbackSagaStep;
import com.commerce.order.service.adapter.saga.FakeInventoryUpdatingSagaStep;
import com.commerce.order.service.common.LoggerTest;
import com.commerce.order.service.common.valueobject.InventoryStatus;
import com.commerce.order.service.common.valueobject.OrderInventoryStatus;
import com.commerce.order.service.order.usecase.InventoryResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

class InventoryUpdatingResponseMessageListenerAdapterTest extends LoggerTest {

    private static final UUID sagaId=UUID.fromString("5bf96862-0c98-41ef-a952-e03d2ded6a6a");

    InventoryUpdatingResponseMessageListenerAdapter adapter;

    public InventoryUpdatingResponseMessageListenerAdapterTest() {
        super(InventoryUpdatingResponseMessageListenerAdapter.class);
    }

    @BeforeEach
    void setUp() {
        adapter = new InventoryUpdatingResponseMessageListenerAdapter(new FakeInventoryUpdatingSagaStep(), new FakeInventoryUpdatingRollbackSagaStep());
    }

    @AfterEach
    protected void cleanUp() {
        cleanUpActions();
    }

    @Test
    void should_updating_inventory_status_available() {
        //given
        var inventoryResponse = buildInventoryResponseWithParameter(sagaId,3L,InventoryStatus.AVAILABLE, OrderInventoryStatus.UPDATING);
        var logMessage = "InventoryResponse is available for Inventory updating action";

        //when
        //then
        assertDoesNotThrow(() -> adapter.updating(inventoryResponse));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_updating_inventory_status_non_available() {
        //given
        var inventoryResponse = buildInventoryResponseWithParameter(sagaId,3L,InventoryStatus.NON_AVAILABLE, OrderInventoryStatus.UPDATING);
        var logMessage = "InventoryResponse is not available for Inventory updating action";

        //when
        //then
        assertDoesNotThrow(() -> adapter.updating(inventoryResponse));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_updating_rollback_inventory_status_available() {
        //given
        var inventoryResponse = buildInventoryResponseWithParameter(sagaId,1L,InventoryStatus.AVAILABLE, OrderInventoryStatus.UPDATING_ROLLBACK);
        var logMessage = "InventoryResponse is available for Inventory updating rollback action";

        //when
        //then
        assertDoesNotThrow(() -> adapter.updatingRollback(inventoryResponse));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_updating_rollback_inventory_status_non_available() {
        //given
        var inventoryResponse = buildInventoryResponseWithParameter(sagaId,1L,InventoryStatus.NON_AVAILABLE, OrderInventoryStatus.UPDATING_ROLLBACK);
        var logMessage = "InventoryResponse is not available for Inventory updating rollback action";

        //when
        //then
        assertDoesNotThrow(() -> adapter.updatingRollback(inventoryResponse));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    private InventoryResponse buildInventoryResponseWithParameter(UUID sagaId,Long orderId,InventoryStatus inventoryStatus, OrderInventoryStatus orderInventoryStatus) {
        return new InventoryResponse(sagaId, orderId,1L, inventoryStatus, orderInventoryStatus, new ArrayList<>());
    }
}
