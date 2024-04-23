package com.commerce.user.service.user.handler;

import com.commerce.user.service.common.LoggerTest;
import com.commerce.user.service.common.exception.UserNotFoundException;
import com.commerce.user.service.user.handler.adapter.FakeUserDataAdapter;
import com.commerce.user.service.user.usecase.UserRetrieve;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author mselvi
 * @Created 21.04.2024
 */

class UserRetrieveUseCaseHandlerTest extends LoggerTest<UserRetrieveUseCaseHandler> {

    UserRetrieveUseCaseHandler userRetrieveUseCaseHandler;

    public UserRetrieveUseCaseHandlerTest() {
        super(UserRetrieveUseCaseHandler.class);
    }


    @BeforeEach
    void setUp() {
        userRetrieveUseCaseHandler = new UserRetrieveUseCaseHandler(new FakeUserDataAdapter());
    }

    @AfterEach
    protected void cleanUp() {
        cleanUpActions();
    }

    @Test
    void should_retrieve() {
        //given
        var userRetrieve = new UserRetrieve(1L);

        //when
        //then
        var user = assertDoesNotThrow(() -> userRetrieveUseCaseHandler.handle(userRetrieve));
        assertEquals(userRetrieve.userId(), user.getId());
        assertNotNull(user);
    }

    @Test
    void should_retrieve_fail_role_not_exist() {
        //given
        var userRetrieve = new UserRetrieve(2L);
        var errorMessage = "User could not be found by id: 2";

        //when
        //then
        var exception = assertThrows(UserNotFoundException.class, () -> userRetrieveUseCaseHandler.handle(userRetrieve));
        assertTrue(exception.getMessage().contains(errorMessage));
    }
}
