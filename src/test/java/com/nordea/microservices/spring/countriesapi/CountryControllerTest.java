package com.nordea.microservices.spring.countriesapi;

import com.nordea.microservices.spring.countriesapi.country.Country;
import com.nordea.microservices.spring.countriesapi.country.CountryController;
import com.nordea.microservices.spring.countriesapi.country.CountryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountryControllerTest {

    @Mock
    private CountryService countryService;

    @InjectMocks
    private CountryController countryController;

    private final List<Country> RealCountries = Arrays.asList(
            TestData.belgium, TestData.belgium
    );

    private final List<Country> fallbackCountries = List.of(
            TestData.elDorado
    );


    @Test
    public void testFindAll_HappyPath() {

        // Mock the service response
        when(countryService.findAll()).thenReturn(Flux.fromIterable(RealCountries));

        // Perform the HTTP GET request and verify the response
        WebTestClient
                .bindToController(countryController)
                .build()
                .get()
                .uri("/api/v1/countries")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Country.class)
                .isEqualTo(RealCountries);
    }

    @Test
    public void testFindAllCountries_Fallback() {

        // Mock the service response for fallback scenario
        when(countryService.findAll()).thenReturn(Flux.fromIterable(fallbackCountries));

        // Perform the HTTP GET request and verify the response for fallback scenario
        WebTestClient
                .bindToController(countryController)
                .build()
                .get()
                .uri("/api/v1/countries")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Country.class)
                .isEqualTo(fallbackCountries);
    }
}
