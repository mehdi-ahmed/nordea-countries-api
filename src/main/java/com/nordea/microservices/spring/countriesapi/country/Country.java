package com.nordea.microservices.spring.countriesapi.country;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Country(String name,
                      @JsonAlias({"alpha2Code"})
                      @JsonProperty("country_code") String code,
                      String capital,
                      String population,
                      @JsonAlias({"flag"})
                      @JsonProperty("flag_file_url") String flagFileUrl) {
}