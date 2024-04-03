package com.commerce.user.service.user.handler.helper;

import com.commerce.user.service.common.DomainComponent;
import com.commerce.user.service.common.exception.UserNotFoundException;
import com.commerce.user.service.user.entity.User;
import com.commerce.user.service.user.port.cache.TokenCachePort;
import com.commerce.user.service.user.port.jpa.UserDataPort;
import com.commerce.user.service.user.port.security.TokenGeneratePort;
import com.commerce.user.service.user.usecase.JwtToken;
import com.commerce.user.service.user.usecase.UserLogin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author mselvi
 * @Created 31.03.2024
 */

@DomainComponent
public class UserLoginHelper {

    private static final Logger logger = LoggerFactory.getLogger(UserLoginHelper.class);

    private final UserDataPort userDataPort;
    private final TokenGeneratePort tokenGeneratePort;
    private final TokenCachePort tokenCachePort;

    public UserLoginHelper(UserDataPort userDataPort, TokenCachePort tokenCachePort, TokenGeneratePort tokenGeneratePort) {
        this.userDataPort = userDataPort;
        this.tokenCachePort = tokenCachePort;
        this.tokenGeneratePort = tokenGeneratePort;
    }

    @Transactional
    public JwtToken login(UserLogin useCase) {
        String email = useCase.email();
        logger.info("User is finding by email: {}", email);
        User user = findUser(email);
        Long userId = user.getId();

        JwtToken jwtToken = tokenGeneratePort.generateToken(user);
        logger.info("JwtToken generated for userId: {}", userId);

        tokenCachePort.put(userId, jwtToken);
        logger.info("JwtToken has written to cache with userId", userId);
        return jwtToken;
    }

    private User findUser(String email) {
        return userDataPort.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(String.format("User could not be found by email: %s", email)));
    }
}
