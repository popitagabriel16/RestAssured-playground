package org.API.Structures.Requests;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PutRequest {

    private String baseUrl;

    public PutRequest(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Response updateUser(String endpoint, String requestBody) {
        return given()
                .baseUri(baseUrl)
                .header("Content-Type", "application/json")
                .body(requestBody)
                .put(endpoint);
    }

}
