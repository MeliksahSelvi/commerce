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
import static org.mockito.Mockito.verify;
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
        JavaPojo javaPojo = new JavaPojo("joe", "doe");
        String expectedJson = "{\"name\":\"joe\",\"surName\":\"doe\"}";

        when(objectMapper.writeValueAsString(javaPojo)).thenReturn(expectedJson);

        //when
        String actualJson = jacksonAdapter.convertDataToJson(javaPojo);

        //then
        verify(objectMapper).writeValueAsString(javaPojo);
        assertEquals(expectedJson, actualJson);
    }

    @Test
    void should_exractDataFromJson() throws JsonProcessingException {
        //given
        String jsonPayload = "{\"name\":\"joe\",\"surName\":\"doe\"}";
        JavaPojo expectedJavaPojo = new JavaPojo("joe", "doe");
        when(objectMapper.readValue(jsonPayload, JavaPojo.class)).thenReturn(expectedJavaPojo);

        //when
        var actualJavaPojo = jacksonAdapter.exractDataFromJson(jsonPayload, JavaPojo.class);

        //then
        verify(objectMapper).readValue(jsonPayload, JavaPojo.class);
        assertEquals(expectedJavaPojo, actualJavaPojo);
    }
}
