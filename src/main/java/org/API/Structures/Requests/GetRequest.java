package org.API.Structures.Requests;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GetRequest {

    private final String baseUrl;

    public GetRequest(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Response getUser(String endpoint) {
        return RestAssured.given()
                .baseUri(baseUrl)
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }

}
