package com.commerce.user.service.user.handler.adapter;

import com.commerce.user.service.user.model.User;
import com.commerce.user.service.user.port.security.TokenGeneratePort;
import com.commerce.user.service.user.usecase.JwtToken;
import org.springframework.beans.factory.annotation.Value;

/**
 * @Author mselvi
 * @Created 23.04.2024
 */

public class FakeTokenGenerateAdapter implements TokenGeneratePort {

    @Value("${commerce.jwt.security.expire-time}")
    private Long EXPIRE_TIME;

    @Override
    public JwtToken generateToken(User user) {
        return new JwtToken(user.getId(), "jwtToken", EXPIRE_TIME);
    }
}
