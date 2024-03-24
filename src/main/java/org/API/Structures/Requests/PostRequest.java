package org.API.Structures.Requests;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PostRequest {

    private String baseUrl;
    private RestAssuredConfig config;

    public PostRequest(String baseUrl) {
        this.baseUrl = baseUrl;
        this.config = RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs("application/json", ContentType.JSON));
    }

    public Response createUser(String endpoint, String requestBody) {
        return given().config(config)
                .baseUri(baseUrl)
                .body(requestBody)
                .when().post(endpoint);
    }

}
