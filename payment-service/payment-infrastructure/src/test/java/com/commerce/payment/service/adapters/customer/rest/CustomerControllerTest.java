package com.commerce.payment.service.adapters.customer.rest;

import com.commerce.payment.service.adapters.customer.rest.dto.CustomerResponse;
import com.commerce.payment.service.adapters.customer.rest.dto.CustomerSaveCommand;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @Author mselvi
 * @Created 24.04.2024
 */

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Sql(value = {"classpath:sql/CustomerControllerTestSetUp.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(value = {"classpath:sql/CustomerControllerTestCleanUp.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
class CustomerControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private static final String BASE_PATH = "/api/v1/customers";

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    void should_findAll() throws Exception {
        var mvcResult = mockMvc.perform(
                get(BASE_PATH).content("").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        var collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, CustomerResponse.class);
        List<CustomerResponse> customerResponseList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), collectionType);

        assertEquals(mvcResult.getResponse().getStatus(), HttpStatus.OK.value());
        assertNotNull(customerResponseList);
    }

    @Test
    void should_findById() throws Exception {
        Long id = 1L;
        var findMvc = mockMvc.perform(
                get(BASE_PATH + "/" + id).content(id.toString()).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        var findResponse = readResponse(findMvc);

        assertEquals(findMvc.getResponse().getStatus(), HttpStatus.OK.value());
        assertEquals(findResponse.id(), id);
    }

    @Test
    void should_save() throws Exception {
        var customerSaveCommand = buildSaveCommand();

        String saveCommandAsStr = objectMapper.writeValueAsString(customerSaveCommand);
        var mvcResult=mockMvc.perform(
                post(BASE_PATH).content(saveCommandAsStr).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated()).andReturn();

        var customerResponse = readResponse(mvcResult);

        assertEquals(mvcResult.getResponse().getStatus(), HttpStatus.CREATED.value());
        assertEquals(customerResponse.firstName(), customerSaveCommand.firstName());
        assertEquals(customerResponse.lastName(), customerSaveCommand.lastName());
        assertEquals(customerResponse.identityNo(), customerSaveCommand.identityNo());
        assertEquals(customerResponse.email(), customerSaveCommand.email());
    }

    private CustomerSaveCommand buildSaveCommand() {
        return new CustomerSaveCommand(null,"testname", "testsurname", "testidentity1",
                "testemail@gmail.com", "testpassword");
    }

    @Test
    void should_deleteById() throws Exception {
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

    private CustomerResponse readResponse(MvcResult mvcResult) throws JsonProcessingException, UnsupportedEncodingException {
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CustomerResponse.class);
    }
}
