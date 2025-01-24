package com.example.Currency.Converter.API.CustomExceptions;

public class ApiNotAvailableException extends Exception{
    public ApiNotAvailableException(String message){
        super(message);
    }

}
