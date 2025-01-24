package com.example.Currency.Converter.API.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CurrencyConversionResponse {

    @JsonProperty("from")
    private String from;

    @JsonProperty("to")
    private String to;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("convertedAmount")
    private BigDecimal convertedAmount;

    public CurrencyConversionResponse(String from, String to, BigDecimal amount, BigDecimal convertedAmount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.convertedAmount = convertedAmount;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getConvertedAmount() {
        return convertedAmount;
    }
}
