package com.commerce.order.service.saga;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.commerce.order.service.appender.MemoryApender;
import com.commerce.order.service.common.exception.InventoryOutboxNotFoundException;
import com.commerce.order.service.common.exception.OrderNotFoundException;
import com.commerce.order.service.common.valueobject.InventoryStatus;
import com.commerce.order.service.common.valueobject.OrderInventoryStatus;
import com.commerce.order.service.order.handler.adapter.FakeInventoryOutboxDataAdapter;
import com.commerce.order.service.order.handler.adapter.FakeSagaHelper;
import com.commerce.order.service.order.usecase.InventoryResponse;
import com.commerce.order.service.adapter.FakeApprovedOrderDataAdapter;
import com.commerce.order.service.saga.helper.InventoryCheckingRollbackHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

class InventoryCheckingRollbackHelperTest {

    private static final UUID sagaId=UUID.fromString("5bf96862-0c98-41ef-a952-e03d2ded6a6a");
    private static final UUID wrongSagaId=UUID.fromString("5bf96862-0c98-41ef-a952-e03d2d");

    InventoryCheckingRollbackHelper inventoryCheckingRollbackHelper;
    MemoryApender memoryApender;

    @BeforeEach
    void setUp(){
        inventoryCheckingRollbackHelper=
                new InventoryCheckingRollbackHelper(new FakeInventoryOutboxDataAdapter(),new FakeApprovedOrderDataAdapter(),new FakeSagaHelper());

        Logger logger = (Logger) LoggerFactory.getLogger(inventoryCheckingRollbackHelper.getClass());
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
    void should_process() {
        //given
        var inventoryResponse = buildInventoryResponseWithParameters(sagaId,1L);
        var logMessage = String.format("InventoryOutbox is updated for inventory checking rollback with sagaId: %s", sagaId);

        //when
        inventoryCheckingRollbackHelper.process(inventoryResponse);

        //then
        assertDoesNotThrow(() -> inventoryCheckingRollbackHelper.process(inventoryResponse));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_process_fail_when_order_not_exist() {
        //given
        var inventoryResponse = buildInventoryResponseWithParameters(sagaId,2L);

        //when
        //then
        var orderNotFoundException = assertThrows(OrderNotFoundException.class, () -> inventoryCheckingRollbackHelper.process(inventoryResponse));
        assertEquals(String.format("Order could not found with id: %d", inventoryResponse.orderId()), orderNotFoundException.getMessage());
    }

    @Test
    void should_process_fail_when_saga_id_wrong() {
        //given
        var inventoryResponse = buildInventoryResponseWithParameters(wrongSagaId,1L);

        //when
        //then
        var inventoryOutboxNotFoundException =
                assertThrows(InventoryOutboxNotFoundException.class, () -> inventoryCheckingRollbackHelper.process(inventoryResponse));
        assertEquals(String.format("InventoryOutbox could not found with sagaId: %s", wrongSagaId), inventoryOutboxNotFoundException.getMessage());
    }

    private InventoryResponse buildInventoryResponseWithParameters(UUID sagaId, Long orderId) {
        return new InventoryResponse(sagaId, orderId, 1L,
                InventoryStatus.AVAILABLE, OrderInventoryStatus.CHECKING, new ArrayList<>());
    }
}
