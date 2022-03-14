package com.patronage.backend.boards;

import com.patronage.backend.Endpoints;
import com.patronage.backend.auth.BaseAuthTest;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class BoardsTest extends BaseAuthTest {

    @Test
    public void getBoardsWithAuthorizationShouldReturnStatusCode200() {
        givenAuthorized()
                .when().get(Endpoints.BOARDS)
                .then().statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void getBoardsWithoutAuthorizationShouldReturnStatusCode403() {
        given()
                .when().get(Endpoints.BOARDS)
                .then().statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
