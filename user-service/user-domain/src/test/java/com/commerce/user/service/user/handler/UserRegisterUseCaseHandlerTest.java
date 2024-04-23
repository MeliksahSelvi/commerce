package com.commerce.user.service.user.handler;

import ch.qos.logback.classic.Level;
import com.commerce.user.service.common.LoggerTest;
import com.commerce.user.service.common.exception.RoleNotFoundException;
import com.commerce.user.service.common.exception.UserDomainException;
import com.commerce.user.service.common.valueobject.RoleType;
import com.commerce.user.service.role.usecase.RoleSave;
import com.commerce.user.service.user.handler.adapter.FakeUserRegisterHelper;
import com.commerce.user.service.user.usecase.UserRegister;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author mselvi
 * @Created 03.04.2024
 */

class UserRegisterUseCaseHandlerTest extends LoggerTest<UserRegisterUseCaseHandler> {

    UserRegisterUseCaseHandler userRegisterUseCaseHandler;

    public UserRegisterUseCaseHandlerTest() {
        super(UserRegisterUseCaseHandler.class);
    }


    @BeforeEach
    void setUp() {
        userRegisterUseCaseHandler = new UserRegisterUseCaseHandler(new FakeUserRegisterHelper());
    }

    @AfterEach
    protected void cleanUp() {
        cleanUpActions();
    }

    @Test
    void should_register() {
        //given
        var userRegister = new UserRegister("unique@gmail.com", "123456", 1L,RoleType.ADMIN);
        var logMessage = "User register action is started for email: unique@gmail.com";

        //when
        var user = userRegisterUseCaseHandler.handle(userRegister);

        //then
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
        assertEquals(userRegister.customerId(), user.getCustomerId());
        assertEquals(userRegister.email(), user.getEmail());
        assertNotEquals(userRegister.password(), user.getPassword());
        assertNotNull(user.getRoleId());
        assertNotNull(user.getId());
    }

    @Test
    void should_register_fail_when_role_not_exist() {
        //given
        var userRegister = new UserRegister("unique@gmail.com", "123456", 1L,RoleType.CUSTOMER);
        var logMessage = "User register action is started for email: unique@gmail.com";
        var errorMessage="Role could not be found by roleType: CUSTOMER";

        //when
        //then
        var exception = assertThrows(RoleNotFoundException.class, () -> userRegisterUseCaseHandler.handle(userRegister));
        assertTrue(exception.getMessage().contains(errorMessage));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_register_fail_when_email_not_uniue() {
        //given
        var userRegister = new UserRegister("already.exist@gmail.com", "123456", 1L,RoleType.ADMIN);
        var logMessage = "User register action is started for email: already.exist@gmail.com";
        var errorMessage="Email already in use: already.exist@gmail.com";

        //when
        //then
        var exception = assertThrows(UserDomainException.class, () -> userRegisterUseCaseHandler.handle(userRegister));
        assertTrue(exception.getMessage().contains(errorMessage));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }
}
