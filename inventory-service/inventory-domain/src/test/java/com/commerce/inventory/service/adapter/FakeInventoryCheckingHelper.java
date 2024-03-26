package com.commerce.inventory.service.adapter;

import com.commerce.inventory.service.saga.helper.InventoryCheckingHelper;

/**
 * @Author mselvi
 * @Created 26.03.2024
 */

public class FakeInventoryCheckingHelper extends InventoryCheckingHelper {
    public FakeInventoryCheckingHelper() {
        super(new FakeOrderOutboxDataAdapter(), new FakeProductCacheAdapter(), new FakeProductDataAdapter(), new FakeJsonAdapter());
    }
}
