package com.patronage.backend;

import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BaseTest extends Endpoints {

    public RequestSpecification givenBodyJson(String json) {
        return given().header("Content-Type", "application/json").body(json);
    }

    public RequestSpecification givenBodyJson(RequestSpecification requestSpecification, String json) {
        return requestSpecification.header("Content-Type", "application/json").body(json);
    }
}
