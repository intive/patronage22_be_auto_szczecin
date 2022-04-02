package com.patronage.backend.boards;

import com.google.gson.JsonObject;
import com.patronage.backend.Endpoints;
import com.patronage.backend.auth.BaseAuthTest;
import io.restassured.path.json.JsonPath;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class BoardsTest extends BaseAuthTest {

    private static final String VALID_BOARD_NAME = "board name";
    private static final String VALID_BOARD_REQUEST_BODY = String.format("{\"name\": \"%s\"}", VALID_BOARD_NAME);

    /*@Test
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
    }*/

    /*public ResponseBody getBoardsOfUserJson() {
        return givenAuthorized()
                .when().get(Endpoints.BOARDS).getBody().prettyPeek();
    }*/

    public JsonPath getBoardsOfUserJsonPath() {
        return givenAuthorized()
                .when().get(Endpoints.BOARDS).jsonPath();
    }

    public List<Integer> getBoardsIdOfUser() {
        return getBoardsOfUserJsonPath().getJsonObject("id");
    }

    public List<String> getBoardsStateOfUser() {
        return getBoardsOfUserJsonPath().getJsonObject("state");
    }

    @Test
    public void getBoardData() {
        System.out.println(getBoardsIdOfUser());
        System.out.println(getBoardsStateOfUser());

        /*for (int id : getBoardsIdOfUser()) {
            System.out.println(givenAuthorized()
                    .when().get(Endpoints.BOARDS + id).getBody().peek());
        }*/
    }

    public JsonPath getBoardJsonPath(int id) {
        return givenAuthorized()
                .when().get(Endpoints.BOARDS + id).jsonPath();
    }

    public JsonPath getBoardDetailsJsonPath() {
        int id = 108;

        return givenAuthorized()
                .when().get(Endpoints.BOARDS + id + "/details").jsonPath();
    }

    public Map<String, String> getBoard() {
        return getBoardJsonPath(109).getJsonObject("board");
    }

    public ArrayList<Map<String, String>> getColumns() {
        return getBoardJsonPath(109).getJsonObject("columns");
    }

    public ArrayList<Map<String, String>> getUsers() {
        return getBoardJsonPath(109).getJsonObject("users");
    }

    public void printBoard() {
        System.out.println("Board: " + getBoard());
        System.out.println("Columns: " + getColumns());
        System.out.println("Users: " + getUsers());
    }

    @Test
    public void printBoards() {
        System.out.println("Board: " + getBoard().keySet());
        System.out.println("Column: " + getColumns().get(0).keySet());
        System.out.println("User: " + getUsers().get(0).keySet());
    }
}
