package com.weather.spec

import com.weather.dto.ErrorResponseDto
import io.restassured.RestAssured
import io.restassured.response.Response
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification
import spock.lang.Unroll

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ErrorHandlingSpec extends Specification {

    @LocalServerPort
    private int port

    def setup() {
        RestAssured.baseURI = "http://localhost:${port}"
    }

    @Unroll
    def "Should return #expectedCode when #scenario"() {
        when: "Make invalid request"
        Response response = RestAssured.given()
                .queryParams(params)
                .get("/v1/current.json")

        then: "Verify error response"
        response.statusCode() == expectedCode

        and: "Validate error structure"
        ErrorResponseDto error = response.as(ErrorResponseDto.class)
        error.error.code == expectedErrorCode
        error.error.message.contains(expectedMessage)

        where:
        scenario                 | params                     || expectedCode | expectedErrorCode | expectedMessage
        "no API key provided"    | [q: "Hanoi"]              || 401          | 1002              | "API key is invalid or not provided."
        "invalid location"       | [q: "InvalidCity", key: "test"] || 400       | 1006              | "No location found matching parameter 'q'"
        "invalid API key"        | [q: "Hanoi", key: "invalid"]   || 403       | 2008              | "API key has been disabled."
        "Internal application error"   | [q: "Hanoi", key: "test"]      || 400       | 9999              | "Internal application error."
    }
}