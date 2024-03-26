package com.commerce.customer.service.adapters.customer.rest;

import com.commerce.customer.service.adapters.customer.rest.dto.CustomerResponse;
import com.commerce.customer.service.adapters.customer.rest.dto.CustomerSaveCommand;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @Author mselvi
 * @Created 25.03.2024
 */

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
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
        MvcResult mvcResult = mockMvc.perform(
                get(BASE_PATH).content("").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, CustomerResponse.class);
        List<CustomerResponse> customerResponseList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), collectionType);

        assertEquals(mvcResult.getResponse().getStatus(), 200);
    }

    @Test
    void should_findById() throws Exception {
        CustomerSaveCommand customerSaveCommand = buildSaveCommand("testidentity2");
        MvcResult saveMvc = saveCustomer(customerSaveCommand);

        CustomerResponse saveResponse = readResponse(saveMvc);

        MvcResult findMvc = mockMvc.perform(
                get(BASE_PATH + "/" + saveResponse.id()).content(saveResponse.id().toString()).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        CustomerResponse findResponse = readResponse(findMvc);

        assertEquals(findMvc.getResponse().getStatus(), HttpStatus.OK.value());
        assertEquals(findResponse.id(), saveResponse.id());
        assertEquals(findResponse.firstName(),saveResponse.firstName());
        assertEquals(findResponse.lastName(),saveResponse.lastName());
        assertEquals(findResponse.identityNo(),saveResponse.identityNo());
        assertEquals(findResponse.email(),saveResponse.email());
    }

    @Test
    void should_save() throws Exception {
        CustomerSaveCommand customerSaveCommand = buildSaveCommand("testidentity1");
        MvcResult mvcResult = saveCustomer(customerSaveCommand);

        CustomerResponse customerResponse = readResponse(mvcResult);

        assertEquals(mvcResult.getResponse().getStatus(), 201);
        assertEquals(customerResponse.firstName(), customerSaveCommand.firstName());
        assertEquals(customerResponse.lastName(), customerSaveCommand.lastName());
        assertEquals(customerResponse.identityNo(), customerSaveCommand.identityNo());
        assertEquals(customerResponse.email(), customerSaveCommand.email());
    }

    private CustomerSaveCommand buildSaveCommand(String testidentity2) {
        return new CustomerSaveCommand("testname", "testsurname", testidentity2,
                "testemail@gmail.com", "testpassword");
    }

    private MvcResult saveCustomer(CustomerSaveCommand customerSaveCommand) throws Exception {
        String saveCommandAsStr = objectMapper.writeValueAsString(customerSaveCommand);
        return mockMvc.perform(
                post(BASE_PATH).content(saveCommandAsStr).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated()).andReturn();
    }

    private CustomerResponse readResponse(MvcResult mvcResult) throws JsonProcessingException, UnsupportedEncodingException {
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CustomerResponse.class);
    }
}
