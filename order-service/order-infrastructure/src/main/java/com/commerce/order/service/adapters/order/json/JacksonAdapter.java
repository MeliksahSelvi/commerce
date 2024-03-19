package com.commerce.order.service.adapters.order.json;

import com.commerce.order.service.common.exception.OrderDomainException;
import com.commerce.order.service.order.port.json.JsonPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * @Author mselvi
 * @Created 19.03.2024
 */

@Service
public class JacksonAdapter implements JsonPort {

    private final ObjectMapper objectMapper;

    public JacksonAdapter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public <T extends Serializable> String convertDataToJson(T data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new OrderDomainException(String.format("Could not create %s object", data.getClass().getName()), e);
        }
    }

    @Override
    public <T extends Serializable> T exractDataFromJson(String payload, Class<T> outputType) {
        try {
            return objectMapper.readValue(payload, outputType);
        } catch (JsonProcessingException e) {
            throw new OrderDomainException(String.format("Could not read %s object! %s", outputType.getName()), e);
        }
    }

}
