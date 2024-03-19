package com.commerce.inventory.service.inventory.port.json;

import java.io.Serializable;

/**
 * @Author mselvi
 * @Created 19.03.2024
 */

public interface JsonPort {

    <T extends Serializable> String convertDataToJson(T data);

    <T extends Serializable> T exractDataFromJson(String payload, Class<T> outputType);
}
