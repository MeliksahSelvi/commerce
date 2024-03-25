package com.commerce.order.service.adapter;

import com.commerce.order.service.order.handler.helper.CreateOrderHelper;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

public class FakeCreateOrderHelper extends CreateOrderHelper {
    public FakeCreateOrderHelper() {
        super(new FakeInventoryOutboxDataAdapter(), new FakeCheckingOrderDataAdapter(), new FakeSagaHelper(), new FakeJsonAdapter(), new FakeInnerRestAdapter());
    }
}
