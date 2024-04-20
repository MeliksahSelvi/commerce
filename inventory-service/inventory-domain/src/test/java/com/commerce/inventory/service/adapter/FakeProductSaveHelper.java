package com.commerce.inventory.service.adapter;

import com.commerce.inventory.service.inventory.handler.helper.ProductSaveHelper;

/**
 * @Author mselvi
 * @Created 20.04.2024
 */

public class FakeProductSaveHelper extends ProductSaveHelper {
    public FakeProductSaveHelper() {
        super(new FakeProductDataAdapter());
    }
}
