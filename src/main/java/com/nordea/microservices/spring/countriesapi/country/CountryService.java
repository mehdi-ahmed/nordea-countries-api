package com.nordea.microservices.spring.countriesapi.country;

import com.nordea.microservices.spring.countriesapi.country.exception.CountryNotFoundException;
import com.nordea.microservices.spring.countriesapi.country.exception.CountryServiceTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class CountryService {

    private final WebClient webClient;

    public static final String COUNTRY_PUBLIC_API_V2 = "https://restcountries.com/v2";

    public CountryService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl(COUNTRY_PUBLIC_API_V2)
                .build();
    }

    private static void retryMechanismLog(Retry.RetrySignal retrySignal) {
        log.warn("Retrying... attempt {}/{}", retrySignal.totalRetries() + 1, 3);
    }

    public Mono<Country[]> getCountryByName(String name) {
        return webClient.get()
                .uri("/name/{name}", name)
                .retrieve()
                .bodyToMono(Country[].class)
                .timeout(Duration.ofSeconds(30))
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(5))
                        .doBeforeRetry(CountryService::retryMechanismLog))
                .onErrorResume(WebClientResponseException.NotFound.class, ex ->
                        Mono.error(new CountryNotFoundException("Country not found with name: " + name)))
                .onErrorResume(WebClientResponseException.class, ex ->
                        Mono.error(new CountryServiceTimeoutException("Connection timed out while fetching country: " + name)));
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
                .timeout(Duration.ofSeconds(30))
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(5))
                        .doBeforeRetry(CountryService::retryMechanismLog))
                .onErrorResume(throwable -> {
                    log.error("Request failed from {} with message {} : ", COUNTRY_PUBLIC_API_V2, throwable.getMessage());

                    if (throwable instanceof WebClientResponseException ||
                            throwable instanceof AsyncRequestTimeoutException) {
                        // pseudo circuit-breaker when there is a HTTP 5XX exception(SSL Handshake, timeOut, etc.)
                        return Flux.fromIterable(imaginaryCountriesCircuitBreakFallback());
                    }
                    return Flux.error(throwable);
                });

    }

    public List<Country> imaginaryCountriesCircuitBreakFallback() {
        Country narnia = Country.builder()
                .name("Narnia")
                .code("XXX")
                .flagFileUrl("/narnia/flag.png")
                .population("0")
                .capital("Unknown")
                .build();

        Country wakanda = Country.builder()
                .name("Wakanda")
                .code("ZZZ")
                .flagFileUrl("/el-dorado/flag.png")
                .population("6,000,000")
                .capital("Birnin Zana")
                .build();
        return Arrays.asList(narnia, wakanda);
    }
}
