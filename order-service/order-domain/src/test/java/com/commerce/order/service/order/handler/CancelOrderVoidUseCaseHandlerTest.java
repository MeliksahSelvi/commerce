package com.commerce.order.service.order.handler;

import com.commerce.order.service.common.exception.OrderNotFoundException;
import com.commerce.order.service.adapter.FakeCancelOrderHelper;
import com.commerce.order.service.order.usecase.CancelOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

class CancelOrderVoidUseCaseHandlerTest {

    CancelOrderVoidUseCaseHandler cancelOrderVoidUseCaseHandler;

    @BeforeEach
    void setUp() {
        cancelOrderVoidUseCaseHandler = new CancelOrderVoidUseCaseHandler(new FakeCancelOrderHelper());
    }

    @Test
    void should_cancel() {
        //given
        var cancelOrder = new CancelOrder(1L);

        //when
        //then
        assertDoesNotThrow(() -> cancelOrderVoidUseCaseHandler.handle(cancelOrder));
    }

    @Test
    void should_cancel_fail_order_not_exist() {
        //given
        var cancelOrder = new CancelOrder(2L);

        //when
        //then
        assertThrows(OrderNotFoundException.class,() -> cancelOrderVoidUseCaseHandler.handle(cancelOrder));
    }
}
