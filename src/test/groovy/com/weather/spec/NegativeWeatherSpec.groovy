package com.weather.spec

import com.weather.WeatherApiClient
import com.weather.WeatherApiException
import com.github.tomakehurst.wiremock.client.WireMock
import spock.lang.Unroll

class NegativeWeatherSpec extends WeatherApiSpec {

    @Unroll
    def "should handle #expectedError error (status: #statusCode)"() {
        given: "Stubbed error response"
        wireMock.stubFor(
                WireMock.get(WireMock.urlEqualTo("/v1/current.json?key=${apiKey}&q=invalid"))
                        .willReturn(WireMock.aResponse()
                                .withStatus(statusCode)
                                .withHeader("Content-Type", "application/json")
                                .withBodyFile(responseFile))
        )

        and: "Initialize client"
        def client = new WeatherApiClient(baseUrl, apiKey)

        when: "Making invalid request"
        client.getCurrentWeather("invalid")

        then: "Should throw exception with proper message"
        def e = thrown(WeatherApiException)
        e.message.contains(expectedError)

        where:
        statusCode | responseFile                   | expectedError
        400       | "error_400_missing_param.json" | "Parameter 'q' not provided"
        400       | "error_400_internal.json"      | "Internal application error"
        403       | "error_403_disabled.json"      | "API key has been disabled"
        401       | "error_401_invalid.json"       | "API key is invalid or not provided"
    }
}