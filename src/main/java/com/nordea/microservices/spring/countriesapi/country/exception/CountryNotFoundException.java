package com.nordea.microservices.spring.countriesapi.country.exception;

public class CountryNotFoundException extends RuntimeException {

    public CountryNotFoundException(String message) {
        super(message);
    }
}
