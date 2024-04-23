package com.commerce.user.service.user.handler;

import ch.qos.logback.classic.Level;
import com.commerce.user.service.common.LoggerTest;
import com.commerce.user.service.common.exception.UserNotFoundException;
import com.commerce.user.service.user.handler.adapter.FakeUserDeleteHelper;
import com.commerce.user.service.user.usecase.UserDelete;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author mselvi
 * @Created 15.04.2024
 */

class UserDeleteVoidUseCaseHandlerTest extends LoggerTest<UserDeleteVoidUseCaseHandler> {

    UserDeleteVoidUseCaseHandler handler;

    public UserDeleteVoidUseCaseHandlerTest() {
        super(UserDeleteVoidUseCaseHandler.class);
    }

    @BeforeEach
    void setUp() {
        handler = new UserDeleteVoidUseCaseHandler(new FakeUserDeleteHelper());
    }

    @AfterEach
    protected void cleanUp() {
        cleanUpActions();
    }

    @Test
    void should_handle() {
        //given
        var userDelete = new UserDelete(1L);
        var logMessage = "User delete action started by id: 1";

        //when
        //then
        assertDoesNotThrow(() -> handler.handle(userDelete));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }


    @Test
    void should_not_handle_when_customer_not_exist() {
        //given
        var userDelete = new UserDelete(2L);
        var logMessage = "User delete action started by id: 2";
        var errorMessage = "User could not be found by id: 2";

        //when
        //then
        var exception = assertThrows(UserNotFoundException.class, () -> handler.handle(userDelete));
        assertTrue(exception.getMessage().contains(errorMessage));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }
}
