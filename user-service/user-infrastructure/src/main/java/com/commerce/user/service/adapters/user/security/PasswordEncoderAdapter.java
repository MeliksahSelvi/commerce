package com.commerce.user.service.adapters.user.security;

import com.commerce.user.service.user.port.security.EncryptingPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @Author mselvi
 * @Created 31.03.2024
 */

@Service
public class PasswordEncoderAdapter implements EncryptingPort {

    private static final Logger logger= LoggerFactory.getLogger(PasswordEncoderAdapter.class);
    private final PasswordEncoder passwordEncoder;

    public PasswordEncoderAdapter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encrypt(String password) {
        String encryptedData = passwordEncoder.encode(password);
        logger.info("Password encrypted through Password Encoder");
        return encryptedData;
    }
}
