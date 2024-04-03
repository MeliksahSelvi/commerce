package com.commerce.user.service.role.handler.helper;

import com.commerce.user.service.common.DomainComponent;
import com.commerce.user.service.common.exception.UserDomainException;
import com.commerce.user.service.common.valueobject.RoleType;
import com.commerce.user.service.role.entity.Role;
import com.commerce.user.service.role.port.RoleDataPort;
import com.commerce.user.service.role.usecase.RoleSave;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 01.04.2024
 */

@DomainComponent
public class RoleSaveHelper {

    private static final Logger logger = LoggerFactory.getLogger(RoleSaveHelper.class);

    private final RoleDataPort roleDataPort;

    public RoleSaveHelper(RoleDataPort roleDataPort) {
        this.roleDataPort = roleDataPort;
    }

    @Transactional
    public Role save(RoleSave useCase) {
        RoleType roleType = useCase.roleType();
        validateRoleTypeUniqueness(roleType);
        logger.info("Role validated by roleType: {}", roleType);

        Role role = buildRole(useCase);

        Role savedRole = roleDataPort.save(role);
        logger.info("Role saved by roleType: {}", roleType);
        return savedRole;
    }

    private void validateRoleTypeUniqueness(RoleType roleType) {
        Optional<Role> roleOptional = roleDataPort.findByRoleType(roleType);
        if (roleOptional.isPresent()) {
            throw new UserDomainException(String.format("This role type: %s has been already using another role", roleType));
        }
    }

    private Role buildRole(RoleSave useCase) {
        return Role.builder()
                .id(useCase.roleId())
                .roleType(useCase.roleType())
                .permissions(useCase.permissions())
                .build();
    }
}
