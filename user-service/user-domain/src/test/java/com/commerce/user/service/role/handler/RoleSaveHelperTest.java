package com.commerce.user.service.role.handler;

import ch.qos.logback.classic.Level;
import com.commerce.user.service.common.LoggerTest;
import com.commerce.user.service.common.exception.UserDomainException;
import com.commerce.user.service.common.valueobject.RoleType;
import com.commerce.user.service.role.handler.adapter.FakeRoleDataAdapter;
import com.commerce.user.service.role.handler.adapter.FakeRoleSaveHelper;
import com.commerce.user.service.role.handler.helper.RoleSaveHelper;
import com.commerce.user.service.role.usecase.RoleSave;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @Author mselvi
 * @Created 03.04.2024
 */

class RoleSaveHelperTest extends LoggerTest<RoleSaveHelper> {

    RoleSaveHelper roleSaveHelper;

    public RoleSaveHelperTest() {
        super(RoleSaveHelper.class);
    }

    @BeforeEach
    void setUp() {
        roleSaveHelper = new RoleSaveHelper(new FakeRoleDataAdapter());
    }

    @AfterEach
    protected void cleanUp() {
        cleanUpActions();
    }

    @Test
    void should_save() {
        //given
        var roleSave = new RoleSave(2L, RoleType.CUSTOMER, "READ");
        var logMessage = "Role saved by roleType: CUSTOMER";

        //when
        var role = roleSaveHelper.save(roleSave);

        //then
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
        assertEquals(roleSave.roleId(), role.getId());
        assertEquals(roleSave.roleType(), role.getRoleType());
        assertEquals(roleSave.permissions(), role.getPermissions());
    }

    @Test
    void should_save_fail_when_role_type_already_exist() {
        //given
        var roleSave = new RoleSave(1L, RoleType.ADMIN, "READ");
        var errorMessage = "This role type: ADMIN has been already using another role";

        //when
        //then
        var exception = assertThrows(UserDomainException.class, () -> roleSaveHelper.save(roleSave));
        assertTrue(exception.getMessage().contains(errorMessage));
    }
}
