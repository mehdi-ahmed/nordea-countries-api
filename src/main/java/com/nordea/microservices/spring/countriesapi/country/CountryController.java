package com.nordea.microservices.spring.countriesapi.country;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.nordea.microservices.spring.countriesapi.country.CountryService.COUNTRY_PUBLIC_API_V2;

@Slf4j
@RestController
@RequestMapping("/api/v1/countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/{name}")
    public Mono<Country[]> getCountryByName(@PathVariable String name) {
        log.info("Retrieving data for '{}' from {} ", name, COUNTRY_PUBLIC_API_V2);
        return countryService.getCountryByName(name);
    }

    @GetMapping
    public Flux<Country> getAllCountries() {
        log.info("Calling GET /countries from: {}", COUNTRY_PUBLIC_API_V2);
        return countryService.findAll();
    }
}
