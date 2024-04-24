package com.commerce.payment.service.payment.adapter;

import com.commerce.payment.service.customer.adapter.FakeCustomerDataAdapter;
import com.commerce.payment.service.payment.handler.helper.AccountSaveHelper;

/**
 * @Author mselvi
 * @Created 20.04.2024
 */

public class FakeAccountSaveHelper extends AccountSaveHelper {
    public FakeAccountSaveHelper() {
        super(new FakeRandomGenerateAdapter(), new FakeCustomerDataAdapter(), new FakeAccountDataAdapter());
    }
}
