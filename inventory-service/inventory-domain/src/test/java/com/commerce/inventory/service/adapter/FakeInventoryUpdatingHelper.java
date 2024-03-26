package com.commerce.inventory.service.adapter;

import com.commerce.inventory.service.saga.helper.InventoryUpdatingHelper;

/**
 * @Author mselvi
 * @Created 26.03.2024
 */

public class FakeInventoryUpdatingHelper extends InventoryUpdatingHelper {
    public FakeInventoryUpdatingHelper() {
        super(new FakeOrderOutboxDataAdapter(), new FakeProductCacheAdapter(), new FakeProductDataAdapter(), new FakeJsonAdapter());
    }
}
