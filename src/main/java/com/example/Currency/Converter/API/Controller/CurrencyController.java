package com.example.Currency.Converter.API.Controller;

import com.example.Currency.Converter.API.CustomExceptions.ApiNotAvailableException;
import com.example.Currency.Converter.API.CustomExceptions.InvalidCurrencyCodeException;
import com.example.Currency.Converter.API.DTO.CurrencyConversionRequest;
import com.example.Currency.Converter.API.Response.CurrencyConversionResponse;
import com.example.Currency.Converter.API.Service.CurrencyService;
import com.example.Currency.Converter.API.Service.CurrencyServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CurrencyController {
    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @PostMapping(value = "/convert", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CurrencyConversionResponse> convertCurrency(@RequestBody CurrencyConversionRequest currencyConversionRequest){
        try{
            CurrencyConversionResponse conversionResponse = currencyService.convertCurrency(currencyConversionRequest);
            return new ResponseEntity(conversionResponse, HttpStatus.OK);
        }catch (InvalidCurrencyCodeException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (ApiNotAvailableException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/rates")
    public ResponseEntity<Map<String, BigDecimal>> getRates(@RequestParam(defaultValue = "USD", required = false) String base){
        try{
            Map<String, BigDecimal> exchangeRates =  currencyService.getRates(base);
            return new ResponseEntity(exchangeRates, HttpStatus.OK);
        }catch (InvalidCurrencyCodeException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (ApiNotAvailableException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
