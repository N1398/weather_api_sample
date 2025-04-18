package com.weather.spec

import com.weather.WeatherApiClient
import com.github.tomakehurst.wiremock.client.WireMock
import spock.lang.Unroll

class PositiveWeatherSpec extends WeatherApiSpec {

    @Unroll
    def "should get correct weather data for #testCity"() {
        given: "Stubbed weather API response"
        stubWeatherApi(testCity, 200, responseFile)

        and: "Initialize client"
        def client = new WeatherApiClient(baseUrl, apiKey)

        when: "Requesting current weather"
        def weatherData = client.getCurrentWeather(testCity)

        then: "Location data should match expected"
        with(weatherData.location) {
            name == expectedLocationName
            country == expectedCountry
            region == expectedRegion
        }

        and: "Current weather data should match expected"
        with(weatherData.current) {
            temp_c == expectedTempC
            condition.text == expectedCondition
            humidity == expectedHumidity
            wind_kph == expectedWindKph
        }

        where:
        testCity   | responseFile          | expectedLocationName | expectedCountry | expectedRegion | expectedTempC | expectedCondition | expectedHumidity | expectedWindKph
        "Djougou"  | "djougou_weather.json" | "Djougou"           | "Benin"         | "Donga"        | 25.9         | "Clear"           | 66               | 7.9
        "Hainan"   | "hainan_weather.json"  | "Hainan"            | "China"         | "Hainan"       | 21.4         | "Fog"             | 99               | 3.6
        "Osaka-Shi"| "osaka_weather.json"   | "Osaka-Shi"         | "Japan"         | "Osaka"        | 17.2         | "Sunny"           | 83               | 3.6
        "Oslo"     | "oslo_weather.json"    | "Oslo"              | "Norway"        | "Oslo"         | 8.3          | "Light rain"      | 87               | 6.5
    }


        def "should get correct weather for Hainan"() {
            given: "Configure WireMock stub"
            wireMock.register(
                    get(urlPathEqualTo("/v1/current.json"))
                            .withQueryParam("q", equalTo("Hainan"))
                            .willReturn(aResponse()
                                    .withStatus(200)
                                    .withHeader("Content-Type", "application/json")
                                    .withBodyFile("hainan_weather.json"))
            )

            and: "Initialize client"
            def client = new WeatherApiClient("http://localhost:${wireMockRuntimeInfo.httpPort}", "test-api-key")

            when: "Request weather data"
            def weather = client.getCurrentWeather("Hainan")

            then: "Verify response"
            with(weather) {
                location.name == "Hainan"
                location.country == "China"
                current.temp_c == 21.4
                current.condition.text == "Fog"
            }
        }
}