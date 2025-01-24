package com.example.Currency.Converter.API.CustomExceptions;

public class InvalidCurrencyCodeException extends Exception{
    public InvalidCurrencyCodeException(String message){
        super(message);
    }

}
