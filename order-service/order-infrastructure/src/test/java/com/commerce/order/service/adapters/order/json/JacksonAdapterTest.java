package com.commerce.order.service.adapters.order.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

@ExtendWith(MockitoExtension.class)
class JacksonAdapterTest {

    @InjectMocks
    private JacksonAdapter jacksonAdapter;

    @Mock
    private ObjectMapper objectMapper;

    @Test
    void should_convertDataToJson() throws IOException {
        //given
        String json = "{\"name\":\"joe\",\"surName\":\"doe\"}";
        var javaPojo = mock(JavaPojo.class);
        when(javaPojo.getName()).thenReturn("joe");
        when(javaPojo.getSurName()).thenReturn("doe");
        when(objectMapper.writeValueAsString(any())).thenReturn(json);
        when(objectMapper.readValue(json, JavaPojo.class)).thenReturn(javaPojo);

        //when
        String result = jacksonAdapter.convertDataToJson(javaPojo);

        //then
        JavaPojo readedValue = objectMapper.readValue(result, JavaPojo.class);
        assertEquals(javaPojo.getName(), readedValue.getName());
        assertEquals(javaPojo.getSurName(), readedValue.getSurName());
    }

    @Test
    void should_exractDataFromJson() throws JsonProcessingException {
        //given
        String json = "{\"name\":\"joe\",\"surName\":\"doe\"}";
        var javaPojo = mock(JavaPojo.class);
        when(objectMapper.writeValueAsString(any())).thenReturn(json);
        when(objectMapper.readValue(json, JavaPojo.class)).thenReturn(javaPojo);

        //when
        var result = jacksonAdapter.exractDataFromJson(json, JavaPojo.class);

        //then
        String writeValueAsString = objectMapper.writeValueAsString(result);
        assertEquals(writeValueAsString, json);
    }
}
