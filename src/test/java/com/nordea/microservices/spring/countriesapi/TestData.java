package com.nordea.microservices.spring.countriesapi;

import com.nordea.microservices.spring.countriesapi.country.Country;

public class TestData {

    public static final Country finland = Country.builder()
            .name("Finland")
            .code("FI")
            .flagFileUrl("http://finland-flag.png")
            .population("69 Millions")
            .capital("Helsinki")
            .build();


    public static final Country belgium = Country.builder()
            .name("Belgium")
            .code("BE")
            .flagFileUrl("http://belgium-flag.png")
            .population("10 Millions")
            .capital("Brussels")
            .build();


    public static final Country narnia = Country.builder()
            .name("Narnia")
            .code("XXX")
            .flagFileUrl("/narnia/flag.png")
            .population("0")
            .capital("Unknown")
            .build();

    public static final Country wakanda = Country.builder()
            .name("Wakanda")
            .code("ZZZ")
            .flagFileUrl("/el-dorado/flag.png")
            .population("6,000,000")
            .capital("Birnin Zana")
            .build();

    public static final Country elDorado = Country.builder()
            .name("El Dorado")
            .code("YYY")
            .flagFileUrl("/flag/to/el-dorado.png")
            .population("UNKNOWN")
            .capital("UNKNOWN")
            .build();
}
