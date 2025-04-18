package com.weather
import groovy.json.JsonSlurper
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class WeatherApiClient {
    private final String baseUrl
    private final String apiKey

    WeatherApiClient(String baseUrl, String apiKey) {
        this.baseUrl = baseUrl
        this.apiKey = apiKey
    }

    def getCurrentWeather(String city) {
        def url = "${baseUrl}/v1/current.json?key=${apiKey}&q=${city}"
        def client = HttpClient.newHttpClient()
        def request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build()

        def response = client.send(request, HttpResponse.BodyHandlers.ofString())

        if (response.statusCode() >= 400) {
            def error = new JsonSlurper().parseText(response.body())
            throw new WeatherApiException("${error.error.code}: ${error.error.message}")
        }

        return new JsonSlurper().parseText(response.body())
    }
}

class WeatherApiException extends RuntimeException {
    WeatherApiException(String message) {
        super(message)
    }
}
