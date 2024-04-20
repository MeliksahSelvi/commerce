package com.commerce.inventory.service.adapter;

import com.commerce.inventory.service.inventory.handler.helper.ProductDeleteHelper;

/**
 * @Author mselvi
 * @Created 20.04.2024
 */

public class FakeProductDeleteHelper extends ProductDeleteHelper {
    public FakeProductDeleteHelper() {
        super(new FakeProductDataAdapter());
    }
}
