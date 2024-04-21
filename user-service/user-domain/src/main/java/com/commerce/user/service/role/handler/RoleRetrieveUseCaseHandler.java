package com.commerce.user.service.role.handler;

import com.commerce.user.service.common.DomainComponent;
import com.commerce.user.service.common.exception.RoleNotFoundException;
import com.commerce.user.service.common.handler.UseCaseHandler;
import com.commerce.user.service.role.entity.Role;
import com.commerce.user.service.role.port.RoleDataPort;
import com.commerce.user.service.role.usecase.RoleRetrieve;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author mselvi
 * @Created 21.04.2024
 */

@DomainComponent
public class RoleRetrieveUseCaseHandler implements UseCaseHandler<Role, RoleRetrieve> {

    private static final Logger logger = LoggerFactory.getLogger(RoleRetrieveUseCaseHandler.class);
    private final RoleDataPort roleDataPort;

    public RoleRetrieveUseCaseHandler(RoleDataPort roleDataPort) {
        this.roleDataPort = roleDataPort;
    }

    @Override
    public Role handle(RoleRetrieve useCase) {
        Role role = findRole(useCase);
        logger.info("Role Retrieved for id : {}", useCase.roleId());
        return role;
    }

    private Role findRole(RoleRetrieve useCase) {
        return roleDataPort.findById(useCase)
                .orElseThrow(() -> new RoleNotFoundException(String.format("Role could not be found by id: %d", useCase.roleId())));
    }
}
