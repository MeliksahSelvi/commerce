package com.commerce.customer.service.handler.adapter;

import com.commerce.customer.service.customer.handler.helper.CustomerDeleteHelper;

/**
 * @Author mselvi
 * @Created 20.04.2024
 */

public class FakeCustomerDeleteHelper extends CustomerDeleteHelper {
    public FakeCustomerDeleteHelper() {
        super(new FakeCustomerDataAdapter());
    }
}
