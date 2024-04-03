package com.commerce.user.service.role.port;

import com.commerce.user.service.common.valueobject.RoleType;
import com.commerce.user.service.role.entity.Role;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 01.04.2024
 */

public interface RoleDataPort {

    Role save(Role role);

    Optional<Role> findByRoleType(RoleType roleType);
}
