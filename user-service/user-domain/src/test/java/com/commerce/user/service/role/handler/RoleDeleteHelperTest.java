package com.commerce.user.service.role.handler;

import ch.qos.logback.classic.Level;
import com.commerce.user.service.common.LoggerTest;
import com.commerce.user.service.common.exception.RoleNotFoundException;
import com.commerce.user.service.role.handler.adapter.FakeRoleDataAdapter;
import com.commerce.user.service.role.handler.adapter.FakeRoleDeleteHelper;
import com.commerce.user.service.role.handler.helper.RoleDeleteHelper;
import com.commerce.user.service.role.usecase.RoleDelete;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @Author mselvi
 * @Created 15.04.2024
 */

class RoleDeleteHelperTest extends LoggerTest<RoleDeleteHelper> {

    RoleDeleteHelper roleDeleteHelper;

    public RoleDeleteHelperTest() {
        super(RoleDeleteHelper.class);
    }

    @BeforeEach
    void setUp() {
        roleDeleteHelper = new RoleDeleteHelper(new FakeRoleDataAdapter());
    }

    @AfterEach
    protected void cleanUp() {
        cleanUpActions();
    }

    @Test
    void should_delete() {
        //given
        var roleDelete = new RoleDelete(1L);
        var logMessage = "Role deleted by id: 1";

        //when
        //then
        assertDoesNotThrow(() -> roleDeleteHelper.delete(roleDelete));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_delete_fail_role_not_exist() {
        //given
        var roleDelete = new RoleDelete(2L);
        var errorMessage = "Role could not be found by roleId: 2";

        //when
        //then
        var exception = assertThrows(RoleNotFoundException.class, () -> roleDeleteHelper.delete(roleDelete));
        assertTrue(exception.getMessage().contains(errorMessage));
    }
}
