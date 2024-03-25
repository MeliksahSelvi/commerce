package com.commerce.order.service.adapter;

import com.commerce.order.service.saga.helper.InventoryCheckingHelper;

/**
 * @Author mselvi
 * @Created 25.03.2024
 */

public class FakeInventoryCheckingHelper extends InventoryCheckingHelper {

    public FakeInventoryCheckingHelper() {
        super(new FakeInventoryOutboxDataAdapter(), new FakePaymentOutboxDataAdapter(), new FakeCheckingOrderDataAdapter(), new FakeSagaHelper(), new FakeJsonAdapter());
    }
}
