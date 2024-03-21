package com.commerce.order.service.order.handler.adapter;

import com.commerce.order.service.order.port.json.JsonPort;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

public class FakeJsonAdapter implements JsonPort {

    @Override
    public <T extends Serializable> String convertDataToJson(T data) {
        return "jsonPayload";
    }

    @Override
    public <T extends Serializable> T exractDataFromJson(String payload, Class<T> outputType) {
        try {
            return outputType.getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
