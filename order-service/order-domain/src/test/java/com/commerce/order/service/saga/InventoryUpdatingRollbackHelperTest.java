package com.commerce.order.service.saga;

import ch.qos.logback.classic.Level;
import com.commerce.order.service.adapter.FakeOrderNotificationMessagePublisherAdapter;
import com.commerce.order.service.adapter.helper.FakeSagaHelper;
import com.commerce.order.service.adapter.order.FakeOrderDataAdapter;
import com.commerce.order.service.adapter.outbox.FakeInventoryOutboxDataAdapter;
import com.commerce.order.service.common.LoggerTest;
import com.commerce.order.service.common.exception.InventoryOutboxNotFoundException;
import com.commerce.order.service.common.exception.OrderNotFoundException;
import com.commerce.order.service.common.valueobject.InventoryStatus;
import com.commerce.order.service.common.valueobject.OrderInventoryStatus;
import com.commerce.order.service.order.usecase.InventoryResponse;
import com.commerce.order.service.saga.helper.InventoryUpdatingRollbackHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

class InventoryUpdatingRollbackHelperTest extends LoggerTest<InventoryUpdatingRollbackHelper> {

    private static final UUID sagaId = UUID.fromString("5bf96862-0c98-41ef-a952-e03d2ded6a6a");
    private static final UUID wrongSagaId = UUID.fromString("5bf96862-0c98-41ef-a952-e03d2d");

    InventoryUpdatingRollbackHelper inventoryUpdatingRollbackHelper;

    public InventoryUpdatingRollbackHelperTest() {
        super(InventoryUpdatingRollbackHelper.class);
    }

    @BeforeEach
    void setUp() {
        inventoryUpdatingRollbackHelper = new InventoryUpdatingRollbackHelper(new FakeOrderNotificationMessagePublisherAdapter(),
                new FakeInventoryOutboxDataAdapter(), new FakeOrderDataAdapter(), new FakeSagaHelper());
    }

    @AfterEach
    void cleanUp() {
        destroy();
    }

    @Test
    void should_process() {
        //given
        var inventoryResponse = buildInventoryResponseWithParameters(sagaId, 1L);
        var logMessage = String.format("InventoryOutbox updated for inventory updating rollback with sagaId: %s", sagaId);

        //when
        inventoryUpdatingRollbackHelper.process(inventoryResponse);

        //then
        assertDoesNotThrow(() -> inventoryUpdatingRollbackHelper.process(inventoryResponse));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_process_fail_when_order_not_exist() {
        //given
        var inventoryResponse = buildInventoryResponseWithParameters(sagaId, 7L);

        //when
        //then
        var orderNotFoundException = assertThrows(OrderNotFoundException.class, () -> inventoryUpdatingRollbackHelper.process(inventoryResponse));
        assertEquals(String.format("Order could not found with id: %d", inventoryResponse.orderId()), orderNotFoundException.getMessage());
    }

    @Test
    void should_process_fail_when_saga_id_wrong() {
        //given
        var inventoryResponse = buildInventoryResponseWithParameters(wrongSagaId, 1L);

        //when
        //then
        var inventoryOutboxNotFoundException =
                assertThrows(InventoryOutboxNotFoundException.class, () -> inventoryUpdatingRollbackHelper.process(inventoryResponse));
        assertEquals(String.format("InventoryOutbox could not found with sagaId: %s", wrongSagaId), inventoryOutboxNotFoundException.getMessage());
    }

    @Test
    void should_rollback() {
        //given
        var inventoryResponse = buildInventoryResponseWithParameters(sagaId, 1L);
        var logMessage = String.format("InventoryOutbox updated for inventory updating rollback with sagaId: %s", sagaId);

        //when
        inventoryUpdatingRollbackHelper.process(inventoryResponse);

        //then
        assertDoesNotThrow(() -> inventoryUpdatingRollbackHelper.rollback(inventoryResponse));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_rollback_fail_when_order_not_exist() {
        //given
        var inventoryResponse = buildInventoryResponseWithParameters(sagaId, 7L);

        //when
        //then
        var orderNotFoundException = assertThrows(OrderNotFoundException.class,
                () -> inventoryUpdatingRollbackHelper.rollback(inventoryResponse));
        assertEquals(String.format("Order could not found with id: %d", inventoryResponse.orderId()), orderNotFoundException.getMessage());
    }

    @Test
    void should_rollback_fail_when_saga_id_wrong() {
        //given
        var inventoryResponse = buildInventoryResponseWithParameters(wrongSagaId, 1L);

        //when
        //then
        var inventoryOutboxNotFoundException =
                assertThrows(InventoryOutboxNotFoundException.class, () -> inventoryUpdatingRollbackHelper.rollback(inventoryResponse));
        assertEquals(String.format("InventoryOutbox could not found with sagaId: %s", wrongSagaId), inventoryOutboxNotFoundException.getMessage());
    }

    private InventoryResponse buildInventoryResponseWithParameters(UUID sagaId, Long orderId) {
        return new InventoryResponse(sagaId, orderId, 1L,
                InventoryStatus.AVAILABLE, OrderInventoryStatus.CHECKING, new ArrayList<>());
    }
}
