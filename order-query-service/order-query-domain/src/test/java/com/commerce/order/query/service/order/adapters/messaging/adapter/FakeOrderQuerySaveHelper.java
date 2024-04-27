package com.commerce.order.query.service.order.adapters.messaging.adapter;

import com.commerce.order.query.service.order.adapters.messaging.helper.OrderQuerySaveHelper;

/**
 * @Author mselvi
 * @Created 27.04.2024
 */

public class FakeOrderQuerySaveHelper extends OrderQuerySaveHelper {
    public FakeOrderQuerySaveHelper() {
        super(new FakeOrderQueryDataAdapter());
    }
}
