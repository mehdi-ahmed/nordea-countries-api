package com.nordea.microservices.spring.countriesapi.country.exception;

public class CountryServiceTimeoutException extends RuntimeException {

    public CountryServiceTimeoutException(String message) {
        super(message);
    }
}
