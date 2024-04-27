package com.commerce.user.service.user.handler.helper;

import com.commerce.user.service.common.DomainComponent;
import com.commerce.user.service.common.exception.RoleNotFoundException;
import com.commerce.user.service.common.exception.UserDomainException;
import com.commerce.user.service.common.valueobject.RoleType;
import com.commerce.user.service.role.model.Role;
import com.commerce.user.service.role.port.RoleDataPort;
import com.commerce.user.service.user.model.User;
import com.commerce.user.service.user.port.jpa.UserDataPort;
import com.commerce.user.service.user.port.security.EncryptingPort;
import com.commerce.user.service.user.usecase.UserRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 31.03.2024
 */

@DomainComponent
public class UserRegisterHelper {

    private static final Logger logger = LoggerFactory.getLogger(UserRegisterHelper.class);
    private final EncryptingPort encryptingPort;
    private final UserDataPort userDataPort;
    private final RoleDataPort roleDataPort;

    public UserRegisterHelper(EncryptingPort encryptingPort, UserDataPort userDataPort, RoleDataPort roleDataPort) {
        this.encryptingPort = encryptingPort;
        this.userDataPort = userDataPort;
        this.roleDataPort = roleDataPort;
    }

    @Transactional
    public User register(UserRegister useCase) {
        String email = useCase.email();
        RoleType roleType = useCase.roleType();

        validateEmailUniqueness(email);

        Role role = findRole(roleType);
        String encryptedPassword = encryptingPort.encrypt(useCase.password());
        logger.info("User password encrypted for email: {}", email);

        User user = buildUser(useCase, encryptedPassword, role.getId());
        User savedUser = userDataPort.save(user);

        logger.info("User saved for email: {} by role type: {}", email, roleType);
        return savedUser;
    }

    private void validateEmailUniqueness(String email) {
        Optional<User> userOptional = userDataPort.findByEmail(email);
        if (userOptional.isPresent()) {
            throw new UserDomainException(String.format("Email already in use: %s", email));
        }
    }

    private Role findRole(RoleType roleType) {
        return roleDataPort.findByRoleType(roleType)
                .orElseThrow(() -> new RoleNotFoundException(String.format("Role could not be found by roleType: %s", roleType)));
    }

    private User buildUser(UserRegister useCase, String encryptedPassword, Long roleId) {
        return User.builder()
                .email(useCase.email())
                .password(encryptedPassword)
                .customerId(useCase.customerId())
                .roleId(roleId)
                .build();
    }
}
