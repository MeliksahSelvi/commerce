package com.commerce.order.query.service.order.adapters.messaging;

import ch.qos.logback.classic.Level;
import com.commerce.order.query.service.common.valueobject.OrderStatus;
import com.commerce.order.query.service.order.adapters.messaging.adapter.FakeOrderQueryDataAdapter;
import com.commerce.order.query.service.order.adapters.messaging.adapter.FakeOrderQuerySaveHelper;
import com.commerce.order.query.service.order.adapters.messaging.helper.OrderQuerySaveHelper;
import com.commerce.order.query.service.order.common.LoggerTest;
import com.commerce.order.query.service.order.model.OrderQuery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @Author mselvi
 * @Created 27.04.2024
 */

class OrderQuerySaveHelperTest extends LoggerTest<OrderQuerySaveHelper> {

    OrderQuerySaveHelper saveHelper;

    public OrderQuerySaveHelperTest() {
        super(OrderQuerySaveHelper.class);
    }

    @BeforeEach
    void setUp() {
        saveHelper = new OrderQuerySaveHelper(new FakeOrderQueryDataAdapter());
    }

    @AfterEach
    protected void cleanUp() {
        cleanUpActions();
    }

    @Test
    void should_processMessage() {
        //given
        var message = new OrderQuery(1L, OrderStatus.CHECKING);
        var logMessage="OrderQuery saved by order id: 1 and orderStatus: CHECKING";

        //when
        //then
        assertDoesNotThrow(() -> saveHelper.save(message));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }
}
