package com.example.Currency.Converter.API;

import com.example.Currency.Converter.API.CustomExceptions.ApiNotAvailableException;
import com.example.Currency.Converter.API.Response.CurrencyApiResponse;
import com.example.Currency.Converter.API.Service.CurrencyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CurrencyServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CurrencyServiceImpl currencyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        currencyService.apiUrl = "https://api.frankfurter.dev/v1/";
    }

//    @Test
//    void testConvertCurrency_Success() throws InvalidCurrencyCodeException, ApiNotAvailableException {
//        CurrencyConversionRequest request = new CurrencyConversionRequest("USD", "EUR", BigDecimal.valueOf(100));
//        CurrencyApiResponse apiResponse = new CurrencyApiResponse();
//        Map<String, BigDecimal> rates = new HashMap<>();
//        rates.put("EUR", BigDecimal.valueOf(0.85));
//        apiResponse.setRates(rates);
//
//        when(restTemplate.exchange(any(String.class), any(), any(), eq(CurrencyApiResponse.class)))
//                .thenReturn(new ResponseEntity<>(apiResponse, HttpStatus.OK));
//
//        CurrencyConversionResponse response = currencyService.convertCurrency(request);
//
//        assertNotNull(response);
//        assertEquals("USD", response.getFrom());
//        assertEquals("EUR", response.getTo());
//        assertEquals(BigDecimal.valueOf(100), response.getAmount());
//        assertEquals(BigDecimal.valueOf(85.00).setScale(2, BigDecimal.ROUND_HALF_UP), response.getConvertedAmount());
//    }

    @Test
    void testGetRates_ApiNotAvailable() {
        String base = "USD";

        when(restTemplate.getForEntity(any(String.class), eq(CurrencyApiResponse.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        assertThrows(ApiNotAvailableException.class, () -> currencyService.getRates(base));
    }
}