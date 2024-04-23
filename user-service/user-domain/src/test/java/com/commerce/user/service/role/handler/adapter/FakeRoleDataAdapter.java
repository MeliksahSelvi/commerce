package com.commerce.user.service.role.handler.adapter;

import com.commerce.user.service.common.valueobject.RoleType;
import com.commerce.user.service.role.entity.Role;
import com.commerce.user.service.role.port.RoleDataPort;
import com.commerce.user.service.role.usecase.RoleDelete;
import com.commerce.user.service.role.usecase.RoleRetrieve;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 23.04.2024
 */

public class FakeRoleDataAdapter implements RoleDataPort {

    private static Long EXIST_ROLE_ID = 1L;
    private static RoleType ALREADY_EXIST_ROLE_TYPE = RoleType.ADMIN;

    @Override
    public Role save(Role role) {
        return Role.builder()
                .id(role.getId())
                .roleType(role.getRoleType())
                .permissions(role.getPermissions())
                .build();
    }

    @Override
    public Optional<Role> findByRoleType(RoleType roleType) {
        if (ALREADY_EXIST_ROLE_TYPE != roleType) {
            return Optional.empty();
        }
        return Optional.of(Role.builder()
                .id(EXIST_ROLE_ID)
                .roleType(ALREADY_EXIST_ROLE_TYPE)
                .permissions("SAVE")
                .build());
    }

    @Override
    public void deleteById(RoleDelete roleDelete) {

    }

    @Override
    public Optional<Role> findById(RoleRetrieve roleRetrieve) {
        if (EXIST_ROLE_ID != roleRetrieve.roleId()) {
            return Optional.empty();
        }
        return Optional.of(Role.builder()
                .id(EXIST_ROLE_ID)
                .roleType(ALREADY_EXIST_ROLE_TYPE)
                .permissions("SAVE")
                .build());
    }
}
