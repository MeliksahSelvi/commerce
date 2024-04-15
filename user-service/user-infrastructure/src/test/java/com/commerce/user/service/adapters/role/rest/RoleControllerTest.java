package com.commerce.user.service.adapters.role.rest;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @Author mselvi
 * @Created 03.04.2024
 */

@ExtendWith(MockitoExtension.class)
@SpringBootTest
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
    void should_save() throws Exception {
        RoleType roleType = RoleType.CUSTOMER;

        MvcResult deleteMvc = mockMvc.perform(
                delete(BASE_PATH + "/" + roleType).content(roleType.toString()).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        RoleSaveCommand roleSaveCommand = new RoleSaveCommand(null, roleType, "");

        String saveCommandAsStr = objectMapper.writeValueAsString(roleSaveCommand);
        MvcResult mvcResult = mockMvc.perform(
                post(BASE_PATH).content(saveCommandAsStr).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated()).andReturn();

        var customerResponse = readResponse(mvcResult);

        assertEquals(deleteMvc.getResponse().getStatus(),HttpStatus.OK.value());
        assertEquals(mvcResult.getResponse().getStatus(), HttpStatus.CREATED.value());
        assertNotNull(customerResponse.roleId());
        assertEquals(customerResponse.roleType(), roleSaveCommand.roleType());
        assertEquals(customerResponse.permissions(), roleSaveCommand.permissions());
    }

    private RoleSaveResponse readResponse(MvcResult mvcResult) throws JsonProcessingException, UnsupportedEncodingException {
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), RoleSaveResponse.class);
    }
}
