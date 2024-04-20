package com.commerce.payment.service.adapter;

import com.commerce.payment.service.payment.handler.helper.AccountDeleteHelper;

/**
 * @Author mselvi
 * @Created 20.04.2024
 */

public class FakeAccountDeleteHelper extends AccountDeleteHelper {
    public FakeAccountDeleteHelper() {
        super(new FakeAccountDataAdapter());
    }
}
