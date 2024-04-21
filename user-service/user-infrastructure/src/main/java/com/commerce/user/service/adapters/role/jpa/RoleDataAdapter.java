package com.commerce.user.service.adapters.role.jpa;

import com.commerce.user.service.adapters.role.jpa.entity.RoleEntity;
import com.commerce.user.service.adapters.role.jpa.repository.RoleEntityRepository;
import com.commerce.user.service.common.valueobject.RoleType;
import com.commerce.user.service.role.entity.Role;
import com.commerce.user.service.role.port.RoleDataPort;
import com.commerce.user.service.role.usecase.RoleDelete;
import com.commerce.user.service.role.usecase.RoleRetrieve;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 01.04.2024
 */

@Service
public class RoleDataAdapter implements RoleDataPort {

    private final RoleEntityRepository repository;

    public RoleDataAdapter(RoleEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public Role save(Role role) {
        var roleEntity = new RoleEntity();
        roleEntity.setId(role.getId());
        roleEntity.setRoleType(role.getRoleType());
        roleEntity.setPermissions(role.getPermissions());
        return repository.save(roleEntity).toModel();
    }

    @Override
    public Optional<Role> findByRoleType(RoleType roleType) {
        Optional<RoleEntity> roleEntityOptional = repository.findByRoleType(roleType);
        return roleEntityOptional.map(RoleEntity::toModel);
    }

    @Override
    public void deleteById(RoleDelete roleDelete) {
        repository.deleteById(roleDelete.roleId());
    }

    @Override
    public Optional<Role> findById(RoleRetrieve roleRetrieve) {
        return repository.findById(roleRetrieve.roleId()).map(RoleEntity::toModel);
    }
}
