package com.commerce.user.service.user.handler;

import com.commerce.user.service.common.DomainComponent;
import com.commerce.user.service.common.handler.UseCaseHandler;
import com.commerce.user.service.user.entity.User;
import com.commerce.user.service.user.handler.helper.UserRegisterHelper;
import com.commerce.user.service.user.usecase.UserRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author mselvi
 * @Created 31.03.2024
 */

@DomainComponent
public class UserRegisterUseCaseHandler implements UseCaseHandler<User, UserRegister> {

    private static final Logger logger = LoggerFactory.getLogger(UserRegisterUseCaseHandler.class);
    private final UserRegisterHelper userRegisterHelper;

    public UserRegisterUseCaseHandler(UserRegisterHelper userRegisterHelper) {
        this.userRegisterHelper = userRegisterHelper;
    }

    @Override
    public User handle(UserRegister useCase) {
        logger.info("User register action is started for email: {}",useCase.email());
        return userRegisterHelper.register(useCase);
    }
}
