package com.patronage.backend.boards;

import com.patronage.backend.Endpoints;
import com.patronage.backend.auth.BaseAuthTest;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class BoardsTest extends BaseAuthTest {

    private static final String VALID_BOARD_NAME = "board name";
    private static final String VALID_BOARD_REQUEST_BODY = String.format("{\"name\": \"%s\"}", VALID_BOARD_NAME);

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

    @Test
    public void postBoardWithAuthorizationShouldReturnStatusCode201() {
        givenBodyJson(givenAuthorized(), VALID_BOARD_REQUEST_BODY)
                .when().post(Endpoints.BOARDS)
                .then().statusCode(HttpStatus.SC_CREATED);
    }

    @Test
    public void postBoardWithAuthorizationShouldReturnProperJson() {
        givenBodyJson(givenAuthorized(), VALID_BOARD_REQUEST_BODY)
                .when().post(Endpoints.BOARDS)
                .then().body("state", equalTo("CREATED"))
                .body("name", equalTo(VALID_BOARD_NAME))
                .body("id", notNullValue());
    }

    @Test
    public void postBoardWithoutAuthorizationShouldReturnStatusCode403() {
        givenBodyJson(VALID_BOARD_REQUEST_BODY)
                .when().post(Endpoints.BOARDS)
                .then().statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    public void postBoardWithEmptyNameShouldReturnStatusCode400() {
        givenBodyJson(givenAuthorized(), "{\"name\": \"\"}")
                .when().post(Endpoints.BOARDS)
                .then().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void postBoardWithNameShorterThan4CharactersShouldReturnStatusCode400() {
        givenBodyJson(givenAuthorized(), "{\"name\": \"abc\"}")
                .when().post(Endpoints.BOARDS)
                .then().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void postBoardWithNameLongerThan64CharactersShouldReturnStatusCode400() {
        givenBodyJson(givenAuthorized(), "{\"name\": \"Lorem ipsum dolor sit amet, consectetur adipiscing elit molestie.\"}")
                .when().post(Endpoints.BOARDS)
                .then().statusCode(HttpStatus.SC_BAD_REQUEST);
    }
}
