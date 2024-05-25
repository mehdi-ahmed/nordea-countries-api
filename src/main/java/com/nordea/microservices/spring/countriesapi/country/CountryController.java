package com.nordea.microservices.spring.countriesapi.country;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/countries")
public class CountryController {

    private final CountryService countryService;
    private static final Logger LOGGER = LoggerFactory.getLogger(CountryController.class);

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/{name}")
    public Mono<Country[]> getCountryByName(@PathVariable String name) {
        LOGGER.info("Getting the country '{}' from {} ", name, CountryService.COUNTRY_PUBLIC_API_V2);
        return countryService.getCountryByName(name);
    }

    @GetMapping
    public Flux<Country> getAllCountries() {
        LOGGER.info("Getting all countries from {} ", CountryService.COUNTRY_PUBLIC_API_V2);
        return countryService.findAll();
    }
}
