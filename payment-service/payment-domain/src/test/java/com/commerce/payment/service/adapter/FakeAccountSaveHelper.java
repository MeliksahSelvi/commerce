package com.commerce.payment.service.adapter;

import com.commerce.payment.service.payment.handler.helper.AccountSaveHelper;

/**
 * @Author mselvi
 * @Created 20.04.2024
 */

public class FakeAccountSaveHelper extends AccountSaveHelper {
    public FakeAccountSaveHelper() {
        super(new FakeRandomGenerateAdapter(), new FakeAccountDataAdapter(), new FakeInnerRestAdapter());
    }
}
