package com.commerce.customer.service.handler.adapter;

import com.commerce.customer.service.customer.port.security.EncryptingPort;

/**
 * @Author mselvi
 * @Created 20.03.2024
 */

public class FakeEncrptingAdapter implements EncryptingPort {

    @Override
    public String encrypt(String password) {
        return "#####";
    }
}
