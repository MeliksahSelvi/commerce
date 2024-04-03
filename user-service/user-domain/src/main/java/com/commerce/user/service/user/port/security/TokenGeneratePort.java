package com.commerce.user.service.user.port.security;

import com.commerce.user.service.user.entity.User;
import com.commerce.user.service.user.usecase.JwtToken;

/**
 * @Author mselvi
 * @Created 31.03.2024
 */

public interface TokenGeneratePort {

    JwtToken generateToken(User user);
}
