package com.example.Currency.Converter.API.Service;

import com.example.Currency.Converter.API.CustomExceptions.ApiNotAvailableException;
import com.example.Currency.Converter.API.CustomExceptions.InvalidCurrencyCodeException;
import com.example.Currency.Converter.API.DTO.CurrencyConversionRequest;
import com.example.Currency.Converter.API.Response.CurrencyConversionResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public interface CurrencyService {

    CurrencyConversionResponse convertCurrency(CurrencyConversionRequest currencyConversionRequest) throws InvalidCurrencyCodeException, ApiNotAvailableException;

    Map<String, BigDecimal> getRates(String base)throws InvalidCurrencyCodeException, ApiNotAvailableException;
}
