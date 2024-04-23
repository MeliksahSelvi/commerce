package com.commerce.user.service.user.handler;

import ch.qos.logback.classic.Level;
import com.commerce.user.service.common.LoggerTest;
import com.commerce.user.service.common.exception.UserNotFoundException;
import com.commerce.user.service.user.handler.adapter.FakeTokenCacheAdapter;
import com.commerce.user.service.user.handler.adapter.FakeTokenGenerateAdapter;
import com.commerce.user.service.user.handler.adapter.FakeUserDataAdapter;
import com.commerce.user.service.user.handler.helper.UserLoginHelper;
import com.commerce.user.service.user.usecase.UserLogin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @Author mselvi
 * @Created 03.04.2024
 */

class UserLoginHelperTest extends LoggerTest<UserLoginHelper> {

    @Value("${commerce.jwt.security.expire-time}")
    private Long EXPIRE_TIME;

    UserLoginHelper userLoginHelper;

    public UserLoginHelperTest() {
        super(UserLoginHelper.class);
    }


    @BeforeEach
    void setUp() {
        userLoginHelper = new UserLoginHelper(new FakeUserDataAdapter(),new FakeTokenCacheAdapter(),new FakeTokenGenerateAdapter());
    }

    @AfterEach
    protected void cleanUp() {
        cleanUpActions();
    }

    @Test
    void should_login() {
        //given
        var userRegister = new UserLogin("already.exist@gmail.com", "123456");
        var logMessage = "JwtToken has written to cache with userId: 1";

        //when
        var jwtToken = userLoginHelper.login(userRegister);

        //then
        assertNotNull(jwtToken.token());
        assertNotNull(jwtToken.userId());
        assertEquals(jwtToken.tokenIssuedTime(), EXPIRE_TIME);
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_login_fail_when_user_not_exist() {
        //given
        var userRegister = new UserLogin("user.login@gmail.com", "123456");
        var logMessage = "User is finding by email: user.login@gmail.com";
        var errorMessage = "User could not be found by email: user.login@gmail.com";

        //when
        //then
        var exception = assertThrows(UserNotFoundException.class, () -> userLoginHelper.login(userRegister));
        assertTrue(exception.getMessage().contains(errorMessage));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }
}
