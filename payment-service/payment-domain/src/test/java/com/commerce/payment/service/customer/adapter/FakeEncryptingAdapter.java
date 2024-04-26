package com.commerce.payment.service.customer.adapter;

import com.commerce.payment.service.customer.port.security.EncryptingPort;

/**
 * @Author mselvi
 * @Created 24.04.2024
 */

public class FakeEncryptingAdapter implements EncryptingPort {

    @Override
    public String encrypt(String password) {
        return "#####";
    }
}
