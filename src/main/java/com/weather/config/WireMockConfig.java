package com.weather.config;

import com.github.tomakehurst.wiremock.client.WireMock;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@Configuration
public class WireMockConfig {
    @Value("${wiremock.port}")
    private int wiremockPort;

    @PostConstruct
    public void init() {
        WireMock.configureFor(wiremockPort);
        loadStubs();
    }

    private void loadStubs() {
        // Загрузка стабов из ресурсов
        WireMock.stubFor(get(urlPathEqualTo("/v1/current.json"))
                .withQueryParam("q", equalTo("Hanoi"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("wiremock/mappings/current/london.json")));

    }
}