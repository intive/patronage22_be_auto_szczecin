package com.patronage.backend.getBoards;

import com.patronage.backend.BaseTest;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class ExampleTest extends BaseTest {

    @Test
    public void statusCodeEquals200() {
        given()
                .when()
                .header("Authorization", authorizationToken)
                .get("/v1/boards")
                .then()
                .statusCode(200);
    }
}
