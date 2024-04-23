package com.commerce.user.service.user.handler.adapter;

import com.commerce.user.service.user.port.security.EncryptingPort;

/**
 * @Author mselvi
 * @Created 23.04.2024
 */

public class FakeEncryptingAdapter implements EncryptingPort {

    @Override
    public String encrypt(String password) {
        return "#####";
    }
}
