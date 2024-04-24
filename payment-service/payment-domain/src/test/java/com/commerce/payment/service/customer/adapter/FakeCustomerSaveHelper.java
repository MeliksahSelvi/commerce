package com.commerce.payment.service.customer.adapter;

import com.commerce.payment.service.account.handler.helper.CustomerSaveHelper;

/**
 * @Author mselvi
 * @Created 24.04.2024
 */
public class FakeCustomerSaveHelper extends CustomerSaveHelper {
    public FakeCustomerSaveHelper() {
        super(new FakeCustomerDataAdapter(), new FakeEncryptingAdapter());
    }
}
