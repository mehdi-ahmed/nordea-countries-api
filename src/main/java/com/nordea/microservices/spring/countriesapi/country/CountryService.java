package com.nordea.microservices.spring.countriesapi.country;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class CountryService {

    private final WebClient webClient;

    public static final String COUNTRY_PUBLIC_API_V2 = "https://restcountries.com/v2";

    public CountryService() {
        this.webClient = WebClient.builder()
                .baseUrl(COUNTRY_PUBLIC_API_V2)
                .build();
    }

    public Mono<Country[]> getCountryByName(String name) {
        return webClient.get()
                .uri("/name/{name}", name)
                .retrieve()
                .bodyToMono(Country[].class);
    }

    public Flux<Country> findAll() {
        return webClient.get()
                .uri("/all")
                .retrieve()
                .bodyToFlux(Country.class)
                .map(country -> new Country.CountryBuilder()
                        .name(country.name())
                        .code(country.code())
                        .build())
                .timeout(Duration.ofSeconds(10));

        // todo add error handling vs Aspects??
        // https://dzone.com/articles/spring-boot-32-replace-your-resttemplate-with-rest
    }
}
