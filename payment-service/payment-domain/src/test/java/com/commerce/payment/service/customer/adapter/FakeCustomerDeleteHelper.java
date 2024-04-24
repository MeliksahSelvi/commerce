package com.commerce.payment.service.customer.adapter;

import com.commerce.payment.service.account.handler.helper.CustomerDeleteHelper;

/**
 * @Author mselvi
 * @Created 24.04.2024
 */


public class FakeCustomerDeleteHelper extends CustomerDeleteHelper {
    public FakeCustomerDeleteHelper() {
        super(new FakeCustomerDataAdapter());
    }
}
