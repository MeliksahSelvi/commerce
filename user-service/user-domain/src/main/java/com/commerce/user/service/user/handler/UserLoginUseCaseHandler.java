package com.commerce.user.service.user.handler;

import com.commerce.user.service.common.DomainComponent;
import com.commerce.user.service.common.handler.UseCaseHandler;
import com.commerce.user.service.user.handler.helper.UserLoginHelper;
import com.commerce.user.service.user.usecase.JwtToken;
import com.commerce.user.service.user.usecase.UserLogin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author mselvi
 * @Created 31.03.2024
 */

@DomainComponent
public class UserLoginUseCaseHandler implements UseCaseHandler<JwtToken, UserLogin> {

    private static final Logger logger = LoggerFactory.getLogger(UserLoginUseCaseHandler.class);
    private final UserLoginHelper userLoginHelper;

    public UserLoginUseCaseHandler(UserLoginHelper userLoginHelper) {
        this.userLoginHelper = userLoginHelper;
    }

    @Override
    public JwtToken handle(UserLogin useCase) {
        logger.info("User login action started for email: {}", useCase.email());
        return userLoginHelper.login(useCase);
    }
}
