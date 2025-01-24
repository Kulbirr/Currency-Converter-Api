package com.example.Currency.Converter.API.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Map;

public class CurrencyApiResponse {

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("base")
    private String base;

    @JsonProperty("date")
    private String date;

    @JsonProperty("rates")
    private Map<String, BigDecimal> rates;

    // Default constructor
    public CurrencyApiResponse() {
    }

    // Constructor with parameters
    public CurrencyApiResponse(BigDecimal amount, String base, String date, Map<String, BigDecimal> rates) {
        this.amount = amount;
        this.base = base;
        this.date = date;
        this.rates = rates;
    }

    // Getters
    public BigDecimal getAmount() {
        return amount;
    }

    public String getBase() {
        return base;
    }

    public String getDate() {
        return date;
    }

    public Map<String, BigDecimal> getRates() {
        return rates;
    }

    @Override
    public String toString() {
        return "CurrencyApiResponse{" +
                "base='" + base + '\'' +
                ", date='" + date + '\'' +
                ", rates=" + rates +
                '}';
    }
}
