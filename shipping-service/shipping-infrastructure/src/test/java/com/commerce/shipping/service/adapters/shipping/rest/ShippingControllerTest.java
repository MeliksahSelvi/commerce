package com.commerce.shipping.service.adapters.shipping.rest;

import com.commerce.shipping.service.adapters.shipping.rest.dto.ForwardProcessCommand;
import com.commerce.shipping.service.adapters.shipping.rest.dto.ForwardProcessResponse;
import com.commerce.shipping.service.common.valueobject.DeliveryStatus;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @Author mselvi
 * @Created 15.04.2024
 */

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Sql(value = {"classpath:sql/ShippingControllerTestSetUp.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(value = {"classpath:sql/ShippingControllerTestCleanUp.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
class ShippingControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private static final String BASE_PATH = "/api/v1/shippings";

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    void should_forward_process() throws Exception {
        var forwardProcessCommand = buildProcessCommand();

        String saveCommandAsStr = objectMapper.writeValueAsString(forwardProcessCommand);
        var mvcResult = mockMvc.perform(
                patch(BASE_PATH).content(saveCommandAsStr).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        var forwardProcessResponse = readResponse(mvcResult);

        assertEquals(mvcResult.getResponse().getStatus(), HttpStatus.OK.value());
        assertEquals(forwardProcessResponse.orderId(), forwardProcessCommand.orderId());
        assertEquals(forwardProcessResponse.deliveryStatus(), forwardProcessCommand.newStatus());
        assertNotEquals(forwardProcessResponse.deliveryStatus(), forwardProcessCommand.oldStatus());
    }

    private ForwardProcessCommand buildProcessCommand() {
        return new ForwardProcessCommand(1L, DeliveryStatus.APPROVED, DeliveryStatus.SHIPPED);
    }

    private ForwardProcessResponse readResponse(MvcResult mvcResult) throws JsonProcessingException, UnsupportedEncodingException {
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ForwardProcessResponse.class);
    }
}
