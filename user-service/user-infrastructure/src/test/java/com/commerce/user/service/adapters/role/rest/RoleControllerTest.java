package com.commerce.user.service.adapters.role.rest;

import com.commerce.user.service.adapters.role.rest.dto.RoleResponse;
import com.commerce.user.service.adapters.role.rest.dto.RoleSaveCommand;
import com.commerce.user.service.adapters.role.rest.dto.RoleSaveResponse;
import com.commerce.user.service.common.valueobject.RoleType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @Author mselvi
 * @Created 03.04.2024
 */

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Sql(value = {"classpath:sql/RoleControllerTestSetUp.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(value = {"classpath:sql/RoleControllerTestCleanUp.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
class RoleControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private static final String BASE_PATH = "/api/v1/roles";

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    void should_findById() throws Exception {
        Long id = 1L;
        var findMvc = mockMvc.perform(
                get(BASE_PATH + "/" + id).content(id.toString()).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        var findResponse = readResponse(findMvc, RoleResponse.class);

        assertEquals(findMvc.getResponse().getStatus(), HttpStatus.OK.value());
        assertEquals(findResponse.id(), id);
    }

    @Test
    void should_save() throws Exception {
        RoleSaveCommand roleSaveCommand = buildSaveCommand();

        String saveCommandAsStr = objectMapper.writeValueAsString(roleSaveCommand);
        MvcResult mvcResult = mockMvc.perform(
                post(BASE_PATH).content(saveCommandAsStr).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated()).andReturn();

        var roleSaveResponse = readResponse(mvcResult, RoleSaveResponse.class);

        assertEquals(mvcResult.getResponse().getStatus(), HttpStatus.CREATED.value());
        assertNotNull(roleSaveResponse.roleId());
        assertNotEquals(roleSaveResponse.roleId(), roleSaveCommand.roleId());
        assertEquals(roleSaveResponse.roleType(), roleSaveCommand.roleType());
        assertEquals(roleSaveResponse.permissions(), roleSaveCommand.permissions());
    }

    @Test
    void should_delete() throws Exception {
        Long id = 2L;
        var deleteMvcResult = mockMvc.perform(
                delete(BASE_PATH + "/" + id).content(id.toString()).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();


        var findMvcResult = mockMvc.perform(
                get(BASE_PATH + "/" + id).content(id.toString()).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is4xxClientError()).andReturn();


        assertEquals(deleteMvcResult.getResponse().getStatus(), HttpStatus.OK.value());
        assertEquals(findMvcResult.getResponse().getStatus(), HttpStatus.NOT_FOUND.value());
    }

    private RoleSaveCommand buildSaveCommand() {
        return new RoleSaveCommand(null, RoleType.MANAGER, "SAVE");
    }

    private <T> T readResponse(MvcResult mvcResult, Class<T> outputType) throws JsonProcessingException, UnsupportedEncodingException {
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), outputType);
    }
}
