package com.commerce.customer.service.handler.adapter;

import com.commerce.customer.service.customer.handler.helper.CustomerSaveHelper;

/**
 * @Author mselvi
 * @Created 20.03.2024
 */

public class FakeCustomerSaveHelper extends CustomerSaveHelper {
    public FakeCustomerSaveHelper() {
        super(new FakeCustomerDataAdapter(), new FakeEncryptingAdapter());
    }
}
