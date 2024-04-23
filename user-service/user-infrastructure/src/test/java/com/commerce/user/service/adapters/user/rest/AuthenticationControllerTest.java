package com.commerce.user.service.adapters.user.rest;

import com.commerce.user.service.adapters.user.rest.dto.JwtTokenResponse;
import com.commerce.user.service.adapters.user.rest.dto.UserLoginCommand;
import com.commerce.user.service.adapters.user.rest.dto.UserRegisterCommand;
import com.commerce.user.service.adapters.user.rest.dto.UserResponse;
import com.commerce.user.service.common.valueobject.RoleType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @Author mselvi
 * @Created 21.04.2024
 */

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Sql(value = {"classpath:sql/AuthenticationControllerTestSetUp.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(value = {"classpath:sql/AuthenticationControllerTestCleanUp.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
class AuthenticationControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private static final String BASE_PATH = "/api/v1/auth";

    @Autowired
    private WebApplicationContext context;

    @Value("${commerce.jwt.security.expire-time}")
    private Long tokenIssuedTime;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    void should_register() throws Exception {
        var registerCommand = buildRegisterCommand();

        String registerCommandAsStr = objectMapper.writeValueAsString(registerCommand);
        var mvcResult = mockMvc.perform(
                post(BASE_PATH + "/register").content(registerCommandAsStr).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated()).andReturn();

        var registerResponse = readResponse(mvcResult, UserResponse.class);

        assertEquals(mvcResult.getResponse().getStatus(), HttpStatus.CREATED.value());
        assertNotNull(registerResponse.id());
        assertNotNull(registerResponse.roleId());
        assertEquals(registerResponse.customerId(), registerCommand.customerId());
        assertEquals(registerResponse.email(), registerCommand.email());
    }

    private UserRegisterCommand buildRegisterCommand() {
        return new UserRegisterCommand("register.user@gmail.com", "123456", 1L, RoleType.CUSTOMER);
    }

    @Test
    void should_login() throws Exception {
        var loginCommand = buildLoginCommand();

        String loginCommandAsStr = objectMapper.writeValueAsString(loginCommand);
        var mvcResult = mockMvc.perform(
                post(BASE_PATH + "/login").content(loginCommandAsStr).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated()).andReturn();

        var tokenResponse = readResponse(mvcResult, JwtTokenResponse.class);

        assertNotNull(tokenResponse.token());
        assertNotNull(tokenResponse.userId());
        assertEquals(tokenIssuedTime, tokenResponse.tokenIssuedTime());
    }

    private UserLoginCommand buildLoginCommand() {
        return new UserLoginCommand("login.user@gmail.com", "123456");
    }

    private <T> T readResponse(MvcResult mvcResult, Class<T> outputType) throws JsonProcessingException, UnsupportedEncodingException {
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), outputType);
    }
}
