package com.commerce.customer.service.adapters.customer.security;

import com.commerce.customer.service.customer.port.security.EncryptingPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Author mselvi
 * @Created 10.03.2024
 */

@Service
public class PasswordEncoderAdapter implements EncryptingPort {

    private static final Logger logger= LoggerFactory.getLogger(PasswordEncoderAdapter.class);
//    private final PasswordEncoder passwordEncoder;//todo add this interface's implementation on security configuration
//
//    public PasswordEncoderAdapter(PasswordEncoder passwordEncoder) {
//        this.passwordEncoder = passwordEncoder;
//    }

    @Override
    public String encrypt(String password) {
//        String encryptedData = passwordEncoder.encode(password);
//        logger.info("Password encrypted through Password Encoder");
//        return encryptedData;
        return password;
    }
}
