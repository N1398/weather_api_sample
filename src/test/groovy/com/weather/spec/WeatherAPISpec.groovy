package com.weather.spec

import com.weather.dto.CurrentWeatherDto
import io.restassured.RestAssured
import io.restassured.response.Response
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification
import spock.lang.Unroll

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class WeatherAPISpec extends Specification {

    @LocalServerPort
    private int port

    def setup() {
        RestAssured.baseURI = "http://localhost:${port}"
    }

    @Unroll
    def "Get current weather for #city should return valid data"() {
        when: "Request current weather"
        Response response = RestAssured.given()
                .queryParam("q", city)
                .queryParam("key", "test-api-key")
                .get("/v1/current.json")

        then: "Verify response"
        response.statusCode() == 200

        and: "Validate response structure"
        CurrentWeatherDto weather = response.as(CurrentWeatherDto.class)
        weather.location.name == city
        weather.current.temp_c instanceof Double
        weather.current.condition.text instanceof String

        where:
        city << ["Hanoi", "Monaco", "Djougou", "Hainan"]
    }
}