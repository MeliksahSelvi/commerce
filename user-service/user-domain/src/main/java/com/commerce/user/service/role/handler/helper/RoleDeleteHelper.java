package com.commerce.user.service.role.handler.helper;

import com.commerce.user.service.common.DomainComponent;
import com.commerce.user.service.common.exception.RoleNotFoundException;
import com.commerce.user.service.role.entity.Role;
import com.commerce.user.service.role.port.RoleDataPort;
import com.commerce.user.service.role.usecase.RoleDelete;
import com.commerce.user.service.role.usecase.RoleRetrieve;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 15.04.2024
 */

@DomainComponent
public class RoleDeleteHelper {

    private static final Logger logger = LoggerFactory.getLogger(RoleDeleteHelper.class);

    private final RoleDataPort roleDataPort;

    public RoleDeleteHelper(RoleDataPort roleDataPort) {
        this.roleDataPort = roleDataPort;
    }

    @Transactional
    public void delete(RoleDelete roleDelete) {
        checkRoleExist(roleDelete);
        roleDataPort.deleteById(roleDelete);
        logger.info("Role deleted by id: {}", roleDelete.roleId());
    }

    private void checkRoleExist(RoleDelete roleDelete) {
        Long roleId = roleDelete.roleId();
        Optional<Role> roleOptional = roleDataPort.findById(new RoleRetrieve(roleId));
        if (roleOptional.isEmpty()) {
            throw new RoleNotFoundException(String.format("Role could not be found by roleId: %d", roleId));
        }
    }
}
