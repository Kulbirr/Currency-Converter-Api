package com.example.Currency.Converter.API.Service;

import com.example.Currency.Converter.API.CustomExceptions.ApiNotAvailableException;
import com.example.Currency.Converter.API.CustomExceptions.InvalidCurrencyCodeException;
import com.example.Currency.Converter.API.DTO.CurrencyConversionRequest;
import com.example.Currency.Converter.API.Response.CurrencyApiResponse;
import com.example.Currency.Converter.API.Response.CurrencyConversionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    private final RestTemplate restTemplate;

    @Value("${currency.api.url}")
    public
    String apiUrl;

    public CurrencyServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public CurrencyConversionResponse convertCurrency(CurrencyConversionRequest currencyConversionRequest) throws InvalidCurrencyCodeException, ApiNotAvailableException {
        // making sure even if the users inputs in lower or mix it get in CAPS
        String from = currencyConversionRequest.getFrom().toUpperCase();
        String to = currencyConversionRequest.getTo().toUpperCase();
        BigDecimal amount = currencyConversionRequest.getAmount();
        // Building the API request URL with required parameters
        String url = UriComponentsBuilder.fromHttpUrl(apiUrl + "latest")
                .queryParam("base", from)
                .queryParam("symbols", to)
                .toUriString();
        System.out.println("Constructed URL: " + url);

        try {
            // Setting headers for JSON response
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            HttpEntity<String> entity = new HttpEntity<>(headers);

//            ResponseEntity<String> rawResponse = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
//            System.out.println("Raw Response Body: " + rawResponse.getBody());


            // Making the API call to fetch to rates
            ResponseEntity<CurrencyApiResponse> currencyApiResponse = restTemplate.exchange(url, HttpMethod.GET, entity, CurrencyApiResponse.class);

            // Debugging the response content
            System.out.println("currencyResponse: " + currencyApiResponse.getBody());

            // Checking if the response is null or invalid
            if (currencyApiResponse == null || !currencyApiResponse.getStatusCode().is2xxSuccessful()) {
                throw new ApiNotAvailableException("Failed to fetch exchange rates from the API.");
            }

            // Getting the response body
            CurrencyApiResponse body = currencyApiResponse.getBody();

            // Extracting the rate of the currency from map using key
            BigDecimal rate = body.getRates().get(to);

            if(rate == null) {
                throw new ApiNotAvailableException("Rate not found for " + to + " with base " + from);
            }

            // Calculating the converted amount
            BigDecimal convertedAmount = rate.multiply(amount);

            // Rounding the amount to 2 decimal places
            BigDecimal bigDecimalAmount = convertedAmount.setScale(2, RoundingMode.HALF_UP);

            return new CurrencyConversionResponse(from, to, amount, bigDecimalAmount);

        } catch (HttpClientErrorException.NotFound e) {
            // Handling invalid currency error (404)
            throw new InvalidCurrencyCodeException("Invalid currency provided: " + e.getMessage());
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // Handling other client/server exceptions
            throw new ApiNotAvailableException("The API returned an error: " + e.getStatusCode());
        } catch (Exception e) {
            // Handling all other exceptions
            throw new ApiNotAvailableException("An unexpected error occurred while calling the API.");
        }
    }



    @Override
    public Map<String, BigDecimal> getRates(String base) throws InvalidCurrencyCodeException, ApiNotAvailableException {
        String url = UriComponentsBuilder.fromHttpUrl(apiUrl + "latest")
                .queryParam("base", base)
                .toUriString();

        try {
//            getting the rates via tha api
            ResponseEntity<CurrencyApiResponse> currencyApiResponseResponse = restTemplate.getForEntity(url, CurrencyApiResponse.class);

            if(!currencyApiResponseResponse.getStatusCode().is2xxSuccessful() || currencyApiResponseResponse.getBody() == null){
                throw new ApiNotAvailableException("Failed to fetch exchange rates from the API.");
            }
            return new HashMap<String, BigDecimal>(currencyApiResponseResponse.getBody().getRates());
        }catch (HttpClientErrorException.NotFound e) {
            // for handling invalid currency error (because when provided invalid currency it gives 404 error)
            throw new InvalidCurrencyCodeException("Invalid currency provided: " + e.getMessage());
        }catch (HttpClientErrorException | HttpServerErrorException e) {
            // Handling other client/server exceptions
            throw new ApiNotAvailableException("The API returned an error: " + e.getStatusCode());
        }
    }

}
