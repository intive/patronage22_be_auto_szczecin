package com.patronage.backend;

import org.junit.Before;

import static io.restassured.RestAssured.given;

public class BaseTest extends Endpoints {

    public String authorizationToken;
    private static final String email = "P20221523@retroboard.pl";
    private static final String password = "0123456789";

    @Before
    public void setUpToken() {
        authorizationToken = given().
                when()
                .queryParam("email", email)
                .queryParam("password", password)
                .post("/v1/login")
                .getHeader("Authorization");
    }


}
