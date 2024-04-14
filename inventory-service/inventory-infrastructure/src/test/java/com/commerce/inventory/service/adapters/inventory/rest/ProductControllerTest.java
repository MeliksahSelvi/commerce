package com.commerce.inventory.service.adapters.inventory.rest;

import com.commerce.inventory.service.adapters.inventory.rest.dto.ProductResponse;
import com.commerce.inventory.service.adapters.inventory.rest.dto.ProductSaveCommand;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
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
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @Author mselvi
 * @Created 03.04.2024
 */

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ProductControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private static final String BASE_PATH = "/api/v1/products";

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

        CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, ProductResponse.class);
        List<ProductResponse> productResponseList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), collectionType);

        assertEquals(mvcResult.getResponse().getStatus(), HttpStatus.OK.value());
    }

    @Test
    void should_findById() throws Exception {
        ProductSaveCommand productSaveCommand = buildSaveCommand();
        MvcResult saveMvc = saveProduct(productSaveCommand);

        ProductResponse saveResponse = readResponse(saveMvc);

        MvcResult findMvc = mockMvc.perform(
                get(BASE_PATH + "/" + saveResponse.id()).content(saveResponse.id().toString()).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        var findResponse = readResponse(findMvc);

        assertEquals(findMvc.getResponse().getStatus(), HttpStatus.OK.value());
        assertEquals(findResponse.id(), findResponse.id());
        assertEquals(findResponse.name(), findResponse.name());//todo remove if unnecessary because the point is testing findbyıd action not save action
        assertEquals(findResponse.price(), findResponse.price());
        assertEquals(findResponse.quantity(), findResponse.quantity());
    }

    @Test
    void should_save() throws Exception {
        //given
        ProductSaveCommand productSaveCommand = buildSaveCommand();
        MvcResult mvcResult = saveProduct(productSaveCommand);

        //when
        var response = readResponse(mvcResult);

        //then
        assertEquals(mvcResult.getResponse().getStatus(),HttpStatus.CREATED.value());
        assertEquals(response.name(), productSaveCommand.name());
        assertEquals(response.price(), productSaveCommand.price());
        assertEquals(response.quantity(), productSaveCommand.quantity());
        assertEquals(response.availability(), productSaveCommand.availability());
    }

    private ProductSaveCommand buildSaveCommand() {
        return new ProductSaveCommand("product-1", BigDecimal.valueOf(100), 1, true);
    }

    private MvcResult saveProduct(ProductSaveCommand productSaveCommand) throws Exception {
        String saveCommandAsStr = objectMapper.writeValueAsString(productSaveCommand);
        return mockMvc.perform(
                post(BASE_PATH).content(saveCommandAsStr).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated()).andReturn();
    }

    private ProductResponse readResponse(MvcResult mvcResult) throws JsonProcessingException, UnsupportedEncodingException {
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ProductResponse.class);
    }
}
