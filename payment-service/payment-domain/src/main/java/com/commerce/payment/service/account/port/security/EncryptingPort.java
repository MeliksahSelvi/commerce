package com.commerce.payment.service.account.port.security;

/**
 * @Author mselvi
 * @Created 24.04.2024
 */

public interface EncryptingPort {

    String encrypt(String password);
}
