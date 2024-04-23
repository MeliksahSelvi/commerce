package com.commerce.user.service.user.handler;

import com.commerce.user.service.common.DomainComponent;
import com.commerce.user.service.common.exception.UserNotFoundException;
import com.commerce.user.service.common.handler.UseCaseHandler;
import com.commerce.user.service.user.entity.User;
import com.commerce.user.service.user.port.jpa.UserDataPort;
import com.commerce.user.service.user.usecase.UserRetrieve;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author mselvi
 * @Created 21.04.2024
 */

@DomainComponent
public class UserRetrieveUseCaseHandler implements UseCaseHandler<User, UserRetrieve> {

    private static final Logger logger = LoggerFactory.getLogger(UserRetrieveUseCaseHandler.class);
    private final UserDataPort userDataPort;

    public UserRetrieveUseCaseHandler(UserDataPort userDataPort) {
        this.userDataPort = userDataPort;
    }

    @Override
    public User handle(UserRetrieve useCase) {
        User user = findUser(useCase);
        logger.info("User retrieved by id: {}", useCase.userId());
        return user;
    }

    private User findUser(UserRetrieve useCase) {
        return userDataPort.findById(useCase)
                .orElseThrow(() -> new UserNotFoundException(String.format("User could not be found by id: %d", useCase.userId())));
    }
}
