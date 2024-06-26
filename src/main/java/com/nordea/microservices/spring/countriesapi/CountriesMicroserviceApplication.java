package com.nordea.microservices.spring.countriesapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class CountriesMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CountriesMicroserviceApplication.class, args);
    }

}
