package com.example.Currency.Converter.API.DTO;

import lombok.*;

import java.math.BigDecimal;

//@Getter
//@Setter
public class CurrencyConversionRequest {
    private String from;
    private String to;
    private BigDecimal amount;

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public CurrencyConversionRequest(String from, String to, BigDecimal amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }
}
