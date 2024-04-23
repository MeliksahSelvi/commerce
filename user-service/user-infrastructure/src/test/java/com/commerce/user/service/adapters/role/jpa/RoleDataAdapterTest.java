package com.commerce.user.service.adapters.role.jpa;

import com.commerce.user.service.adapters.role.jpa.entity.RoleEntity;
import com.commerce.user.service.adapters.role.jpa.repository.RoleEntityRepository;
import com.commerce.user.service.common.valueobject.RoleType;
import com.commerce.user.service.role.entity.Role;
import com.commerce.user.service.role.usecase.RoleDelete;
import com.commerce.user.service.role.usecase.RoleRetrieve;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @Author mselvi
 * @Created 03.04.2024
 */

@ExtendWith(MockitoExtension.class)
class RoleDataAdapterTest {

    @InjectMocks
    private RoleDataAdapter roleDataAdapter;

    @Mock
    private RoleEntityRepository roleEntityRepository;


    @Test
    void should_save() {
        //given
        var role = buildRole();
        var roleEntity = mock(RoleEntity.class);
        when(roleEntityRepository.save(any())).thenReturn(roleEntity);
        when(roleEntity.toModel()).thenReturn(role);

        //when
        var savedRole = roleDataAdapter.save(role);

        //then
        assertEquals(role.getId(),savedRole.getId());
        assertEquals(role.getRoleType(),savedRole.getRoleType());
        assertEquals(role.getPermissions(),savedRole.getPermissions());
    }

    @Test
    void should_findById() {
        //given
        var retrieve = new RoleRetrieve(1L);
        when(roleEntityRepository.findById(retrieve.roleId())).thenReturn(Optional.of(buildRoleEntity()));

        //when
        var roleOptional = roleDataAdapter.findById(retrieve);

        //then
        assertTrue(roleOptional.isPresent());
        assertEquals(retrieve.roleId(), roleOptional.get().getId());
    }

    @Test
    void should_findById_empty() {
        //given
        var retrieve = new RoleRetrieve(1L);
        when(roleEntityRepository.findById(retrieve.roleId())).thenReturn(Optional.empty());

        //when
        var customerOptional = roleDataAdapter.findById(retrieve);

        //then
        assertTrue(customerOptional.isEmpty());
    }

    @Test
    void should_findByRoleType() {
        //given
        var roleType = RoleType.ADMIN;
        when(roleEntityRepository.findByRoleType(roleType)).thenReturn(Optional.of(buildRoleEntity()));

        //when
        var roleOptional = roleDataAdapter.findByRoleType(roleType);

        //then
        assertTrue(roleOptional.isPresent());
        assertEquals(roleType, roleOptional.get().getRoleType());
    }

    @Test
    void should_findByRoleType_empty() {
        //given
        var roleType = RoleType.ADMIN;
        when(roleEntityRepository.findByRoleType(roleType)).thenReturn(Optional.empty());

        //when
        var roleOptional = roleDataAdapter.findByRoleType(roleType);

        //then
        assertTrue(roleOptional.isEmpty());
    }

    @Test
    void should_deleteById() {
        //given
        var roleDelete = new RoleDelete(1L);

        //when
        roleDataAdapter.deleteById(roleDelete);

        //then
        assertEquals(Optional.empty(),roleDataAdapter.findById(new RoleRetrieve(1L)));
        verify(roleEntityRepository).deleteById(roleDelete.roleId());
    }

    private Role buildRole() {
        return Role.builder()
                .id(1L)
                .roleType(RoleType.ADMIN)
                .permissions("SAVE")
                .build();
    }

    private RoleEntity buildRoleEntity() {
        var customerEntity = new RoleEntity();
        customerEntity.setId(1L);
        customerEntity.setRoleType(RoleType.ADMIN);
        customerEntity.setPermissions("SAVE");
        return customerEntity;
    }
}
