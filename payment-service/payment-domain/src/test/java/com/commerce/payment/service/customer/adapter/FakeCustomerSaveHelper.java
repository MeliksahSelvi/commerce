package com.commerce.payment.service.customer.adapter;

import com.commerce.payment.service.customer.handler.helper.CustomerSaveHelper;

/**
 * @Author mselvi
 * @Created 24.04.2024
 */
public class FakeCustomerSaveHelper extends CustomerSaveHelper {
    public FakeCustomerSaveHelper() {
        super(new FakeCustomerCommandMessagePublisherAdapter(), new FakeCustomerDataAdapter(), new FakeEncryptingAdapter());
    }
}
