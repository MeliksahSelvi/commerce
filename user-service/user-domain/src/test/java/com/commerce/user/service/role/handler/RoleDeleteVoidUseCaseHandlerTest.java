package com.commerce.user.service.role.handler;

import ch.qos.logback.classic.Level;
import com.commerce.user.service.common.LoggerTest;
import com.commerce.user.service.common.exception.RoleNotFoundException;
import com.commerce.user.service.role.handler.adapter.FakeRoleDeleteHelper;
import com.commerce.user.service.role.usecase.RoleDelete;
import com.commerce.user.service.role.usecase.RoleRetrieve;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author mselvi
 * @Created 15.04.2024
 */

class RoleDeleteVoidUseCaseHandlerTest extends LoggerTest<RoleDeleteVoidUseCaseHandler> {

    RoleDeleteVoidUseCaseHandler handler;

    public RoleDeleteVoidUseCaseHandlerTest() {
        super(RoleDeleteVoidUseCaseHandler.class);
    }

    @BeforeEach
    void setUp() {
        handler = new RoleDeleteVoidUseCaseHandler(new FakeRoleDeleteHelper());
    }

    @AfterEach
    protected void cleanUp() {
        cleanUpActions();
    }

    @Test
    void should_delete() {
        //given
        var roleDelete = new RoleDelete(1L);
        var logMessage = "Role delete action started by id: 1";

        //when
        //then
        assertDoesNotThrow(() -> handler.handle(roleDelete));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_delete_fail_role_not_exist() {
        //given
        var roleDelete = new RoleDelete(2L);
        var logMessage = "Role delete action started by id: 2";
        var errorMessage = "Role could not be found by roleId: 2";

        //when
        //then
        var exception = assertThrows(RoleNotFoundException.class, () -> handler.handle(roleDelete));
        assertTrue(exception.getMessage().contains(errorMessage));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }
}
