package com.patronage.backend.boards;

import com.patronage.backend.Endpoints;
import com.patronage.backend.auth.BaseAuthTest;
import io.restassured.path.json.JsonPath;
import org.apache.http.HttpStatus;
import org.junit.Test;

import java.util.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.equalTo;
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

    public JsonPath getBoardsOfUserJsonPath() {
        return givenAuthorized()
                .when().get(Endpoints.BOARDS).jsonPath();
    }

    public List<Integer> getBoardsIds() {
        return getBoardsOfUserJsonPath().getJsonObject("id");
    }

    /*public List<String> getBoardsStates() {
        return getBoardsOfUserJsonPath().getJsonObject("state");
    }*/

    public JsonPath getBoardJsonPath(int id) {
        return givenAuthorized()
                .when().get(Endpoints.BOARDS + id).jsonPath();
    }

    /*public JsonPath getBoardJsonPathUnauthorized(int id) {
        return givenAuthorized()
                .when().get(Endpoints.BOARDS + id).jsonPath();
    }*/

    /*public JsonPath getBoardDetailsJsonPath(int id) {
        //id = 108;

        return givenAuthorized()
                .when().get(Endpoints.BOARDS + id + "/details").jsonPath();
    }*/

    public Map<String, String> getBoard(int id) {
        return getBoardJsonPath(id).getJsonObject("board");
    }

    /*public String getBoardUnauthorized(int id) {
        return getBoardJsonPathUnauthorized(id).get();
    }

    //@Test
    public void printError() {
        System.out.println(getBoard(155).values());
    }*/

    public ArrayList<Map<String, String>> getColumns(int id) {
        return getBoardJsonPath(id).getJsonObject("columns");
    }

    public ArrayList<Map<String, String>> getUsers(int id) {
        return getBoardJsonPath(id).getJsonObject("users");
    }

    /*public List<Map<String, String>> getDetails(int id) {
        return getBoardDetailsJsonPath(id).get();
    }*/

    public List<String> getBoardKeysToCompare() {
        return Arrays.asList("id", "state", "name", "numberOfVotes");
    }

    public List<String> getColumnsKeysToCompare() {
        return Arrays.asList("name", "id", "position", "colour");
    }

    public List<String> getUsersKeysToCompare() {
        return Arrays.asList("email", "id");
    }

    public boolean checkBoardKeys(int id) {
        List<String> keys = List.copyOf(getBoard(id).keySet());

        if (!keys.equals(getBoardKeysToCompare())) {
            System.err.println("Error in board");
            return false;
        }
        return true;
    }

    public boolean checkColumnsKeys(int id) {
        for (Map<String, String> column : getColumns(id)) {
            List<String> keys = List.copyOf(column.keySet());

            if (!keys.equals(getColumnsKeysToCompare())) {
                System.err.print("Error in columns");
                return false;
            }
        }
        return true;
    }

    public boolean checkUsersKeys(int id) {
        for (Map<String, String> user : getUsers(id)) {
            List<String> keys = List.copyOf(user.keySet());

            if (!keys.equals(getUsersKeysToCompare())) {
                System.err.print("Error in users");
                return false;
            }
        }
        return true;
    }

    /*public boolean checkResponses(int id) {
        if (givenAuthorized()
                .when().get(Endpoints.BOARDS + id).getStatusCode() != HttpStatus.SC_OK) {
            System.err.println("Error in response");
            return false;
        }
        return true;
    }*/

    public boolean checkBoardsKeys() {
        for (int id : getBoardsIds()) {
            if (!(checkBoardKeys(id) && checkColumnsKeys(id) && checkUsersKeys(id)/* && checkResponses(id)*/)) {
                System.err.println("Error in board " + id);
                return false;
            }
        }
        return true;
    }

    @Test
    public void checkAllBoardsOfUserDisplayingKeys() {
        assertThat(checkBoardsKeys(), equalTo(true));
    }
}
