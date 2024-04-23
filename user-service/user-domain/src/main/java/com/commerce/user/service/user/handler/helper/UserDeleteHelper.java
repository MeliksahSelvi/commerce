package com.commerce.user.service.user.handler.helper;

import com.commerce.user.service.common.DomainComponent;
import com.commerce.user.service.common.exception.UserNotFoundException;
import com.commerce.user.service.user.entity.User;
import com.commerce.user.service.user.port.jpa.UserDataPort;
import com.commerce.user.service.user.usecase.UserDelete;
import com.commerce.user.service.user.usecase.UserRetrieve;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 15.04.2024
 */

@DomainComponent
public class UserDeleteHelper {

    private static final Logger logger = LoggerFactory.getLogger(UserDeleteHelper.class);

    private final UserDataPort userDataPort;

    public UserDeleteHelper(UserDataPort userDataPort) {
        this.userDataPort = userDataPort;
    }

    @Transactional
    public void delete(UserDelete userDelete) {
        checkUserExist(userDelete);
        userDataPort.deleteById(userDelete);
        logger.info("User deleted by id: {}", userDelete.userId());
    }

    private void checkUserExist(UserDelete userDelete) {
        Long userId = userDelete.userId();
        Optional<User> userOptional = userDataPort.findById(new UserRetrieve(userId));
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(String.format("User could not be found by id: %d", userId));
        }
    }
}
