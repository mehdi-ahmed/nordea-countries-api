package com.nordea.microservices.spring.countriesapi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@JsonTest
public class CustomJsonDeserializerApplicationTests {

    // we need:
    // 1. name->common
    // 2. cca2
    // 3 .capital - let's assume there is only one capital(Pick first [0]
    // 4. population
    // 5. flags -> pick "png"

    @Value("classpath:data/country.json")
    Resource countryJsonResource;

    @Autowired
    ObjectMapper objectMapper;

    String countryJsonObject;

    @Test
    void contextLoad() {
        assertNotNull(objectMapper);
    }

    @BeforeEach
    void setUp() throws IOException {

        countryJsonObject = new String(Files.readAllBytes(countryJsonResource.getFile().toPath()));
        JsonNode jsonNode = objectMapper.readTree(countryJsonObject);
        System.out.println(jsonNode.toPrettyString());
    }

}
