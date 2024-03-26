package com.commerce.payment.service.adapters.payment.generate;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

class ApacheRandomGenerateAdapterTest {

    @Mock
    private ApacheRandomGenerateAdapter adapter = mock(ApacheRandomGenerateAdapter.class);


    @Test
    void should_randomNumericAsString() {
        //given
        int charCount = 10;
        String expectedResult = "1234567890";
        when(adapter.randomNumericAsString(charCount)).thenReturn(expectedResult);

        //when
        String actualResult = adapter.randomNumericAsString(charCount);

        //then
        verify(adapter).randomNumericAsString(charCount);
        assertEquals(expectedResult, actualResult, "Generated numeric string is incorrect");
    }

}
