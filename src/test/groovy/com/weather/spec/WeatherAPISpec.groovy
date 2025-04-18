package com.weather.spec
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import spock.lang.Specification

@WireMockTest
abstract class WeatherApiSpec extends Specification {
    def objectMapper = new ObjectMapper()
    def apiKey = "test-api-key"
    def baseUrl

    def setup() {
        baseUrl = "http://localhost:${wireMockRuntimeInfo.httpPort}"
    }

    def stubWeatherApi(String city, int status, String responseFile) {
        def url = "/v1/current.json?key=${apiKey}&q=${city}"
        wireMock.stubFor(
                WireMock.get(WireMock.urlPathEqualTo("/v1/current.json"))
                        .withQueryParam("key", WireMock.equalTo(apiKey))
                        .withQueryParam("q", WireMock.equalTo(city))
                        .willReturn(WireMock.aResponse()
                                .withStatus(status)
                                .withHeader("Content-Type", "application/json")
                                .withBodyFile(responseFile))
        )
    }

    def compareWeatherData(actual, expected) {
        def differences = [:]

        expected.each { key, value ->
            if (actual[key] != value) {
                differences[key] = [
                        expected: value,
                        actual: actual[key]
                ]
            }
        }

        if (differences) {
            println "Differences for ${expected.location.name}:"
            differences.each { key, diff ->
                println "  $key: expected ${diff.expected}, got ${diff.actual}"
            }
        }

        return differences.isEmpty()
    }
}