package com.commerce.user.service.adapters.role.jpa.repository;

import com.commerce.user.service.adapters.role.jpa.entity.RoleEntity;
import com.commerce.user.service.common.valueobject.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 01.04.2024
 */

@Repository
public interface RoleEntityRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByRoleType(RoleType roleType);
}
