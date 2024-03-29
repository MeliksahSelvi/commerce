package com.commerce.order.service.order.handler;

import com.commerce.order.service.common.exception.OrderNotFoundException;
import com.commerce.order.service.adapter.helper.FakeCancelOrderHelper;
import com.commerce.order.service.order.handler.helper.CancelOrderHelper;
import com.commerce.order.service.order.usecase.CancelOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

class CancelOrderHelperTest {

    CancelOrderHelper cancelOrderHelper;

    @BeforeEach
    void setUp(){
        cancelOrderHelper=new FakeCancelOrderHelper();
    }

    @Test
    void should_cancel() {
        //given
        var cancelOrder = new CancelOrder(1L);

        //when
        //then
        assertDoesNotThrow(() -> cancelOrderHelper.cancelOrder(cancelOrder));
    }

    @Test
    void should_cancel_fail_order_not_exist() {
        //given
        var cancelOrder = new CancelOrder(7L);

        //when
        //then
        assertThrows(OrderNotFoundException.class,() -> cancelOrderHelper.cancelOrder(cancelOrder));
    }
}
