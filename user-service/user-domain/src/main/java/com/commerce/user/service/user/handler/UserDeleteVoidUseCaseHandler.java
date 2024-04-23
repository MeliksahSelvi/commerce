package com.commerce.user.service.user.handler;

import com.commerce.user.service.common.DomainComponent;
import com.commerce.user.service.common.handler.VoidUseCaseHandler;
import com.commerce.user.service.user.handler.helper.UserDeleteHelper;
import com.commerce.user.service.user.usecase.UserDelete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author mselvi
 * @Created 15.04.2024
 */

@DomainComponent
public class UserDeleteVoidUseCaseHandler implements VoidUseCaseHandler<UserDelete> {

    private static final Logger logger = LoggerFactory.getLogger(UserDeleteVoidUseCaseHandler.class);

    private final UserDeleteHelper userDeleteHelper;

    public UserDeleteVoidUseCaseHandler(UserDeleteHelper userDeleteHelper) {
        this.userDeleteHelper = userDeleteHelper;

    }

    @Override
    public void handle(UserDelete useCase) {
        logger.info("User delete action started by id: {}", useCase.userId());
        userDeleteHelper.delete(useCase);
    }
}
