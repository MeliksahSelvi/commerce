package com.commerce.order.service.adapters.order.rest;

import com.commerce.order.service.adapters.order.rest.dto.AddressDto;
import com.commerce.order.service.adapters.order.rest.dto.OrderCreateCommand;
import com.commerce.order.service.adapters.order.rest.dto.OrderCreateResponse;
import com.commerce.order.service.adapters.order.rest.dto.OrderItemDto;
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
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @Author mselvi
 * @Created 15.04.2024
 */

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Sql(value = {"classpath:sql/OrderControllerTestSetUp.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(value = {"classpath:sql/OrderControllerTestCleanUp.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
class OrderControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private static final String BASE_PATH = "/api/v1/orders";

    @Autowired
    private WebApplicationContext context;


    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    void should_createOrder() throws Exception {
        var orderCreateCommand = buildOrderCreateCommand();

        String createCommandAsJson = objectMapper.writeValueAsString(orderCreateCommand);
        var mvcResult = mockMvc.perform(
                post(BASE_PATH).content(createCommandAsJson).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated()).andReturn();

        var orderCreateResponse = readResponse(mvcResult, OrderCreateResponse.class);
        assertEquals(mvcResult.getResponse().getStatus(), HttpStatus.CREATED.value());
        assertNotNull(orderCreateResponse.orderId());
    }

    private OrderCreateCommand buildOrderCreateCommand() {
        return new OrderCreateCommand(3L, BigDecimal.TEN, List.of(new OrderItemDto(1L, 1, BigDecimal.TEN, BigDecimal.TEN))
                , new AddressDto("ist", "sck", "abd", "ytk", "34000"));
    }

    @Test
    void should_cancelOrder() throws Exception {
        Long id = 101L;
        var patchMvc = mockMvc.perform(
                patch(BASE_PATH + "/" + id).content(id.toString()).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        var trackResponse = patchMvc.getResponse().getContentAsString();

        assertEquals(patchMvc.getResponse().getStatus(), HttpStatus.OK.value());
        assertEquals("Order cancel action has been starting", trackResponse);
    }

    private <T> T readResponse(MvcResult mvcResult, Class<T> outputType) throws JsonProcessingException, UnsupportedEncodingException {
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), outputType);
    }
}
