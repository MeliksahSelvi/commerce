package com.commerce.user.service.role.handler;

import com.commerce.user.service.common.LoggerTest;
import com.commerce.user.service.common.exception.RoleNotFoundException;
import com.commerce.user.service.role.handler.adapter.FakeRoleDataAdapter;
import com.commerce.user.service.role.usecase.RoleRetrieve;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author mselvi
 * @Created 21.04.2024
 */

class RoleRetrieveUseCaseHandlerTest extends LoggerTest<RoleRetrieveUseCaseHandler> {

    RoleRetrieveUseCaseHandler roleRetrieveUseCaseHandler;

    public RoleRetrieveUseCaseHandlerTest() {
        super(RoleRetrieveUseCaseHandler.class);
    }


    @BeforeEach
    void setUp() {
        roleRetrieveUseCaseHandler = new RoleRetrieveUseCaseHandler(new FakeRoleDataAdapter());
    }

    @AfterEach
    protected void cleanUp() {
        cleanUpActions();
    }

    @Test
    void should_retrieve() {
        //given
        var roleRetrieve = new RoleRetrieve(1L);

        //when
        //then
        var role=assertDoesNotThrow(()->roleRetrieveUseCaseHandler.handle(roleRetrieve));
        assertEquals(roleRetrieve.roleId(), role.getId());
        assertNotNull(role);
    }

    @Test
    void should_retrieve_fail_role_not_exist() {
        //given
        var customerRetrieve = new RoleRetrieve(2L);
        var errorMessage="Role could not be found by id: 2";

        //when
        //then
        var exception = assertThrows(RoleNotFoundException.class, () -> roleRetrieveUseCaseHandler.handle(customerRetrieve));
        assertTrue(exception.getMessage().contains(errorMessage));
    }
}
