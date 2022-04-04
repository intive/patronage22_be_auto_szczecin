package com.patronage.backend.boards;

import com.patronage.backend.Endpoints;
import com.patronage.backend.auth.BaseAuthTest;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Test;

import java.util.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.equalTo;

public class GetBoardByIdTest extends BaseAuthTest {

    public Response getBoardsOfUser() {
        return givenAuthorized()
                .when().get(Endpoints.BOARDS);
    }

    public Map<String, String> getBoardNotAssignedUser() {
        Random rand = new Random();
        int tries = 0;
        int randomId;

        do {
            randomId = rand.nextInt(300);
            tries++;
        }
        while (givenAuthorized()
                .when().get(Endpoints.BOARDS + randomId).getStatusCode() != HttpStatus.SC_BAD_REQUEST || tries == 50);

        return givenAuthorized()
                .when().get(Endpoints.BOARDS + randomId).jsonPath().get();
    }

    public Map<String, String> getBoardNotAuthorized() {
        Random rand = new Random();
        int randomId;
        int tries = 0;

        do {
            randomId = rand.nextInt(300);
            tries++;
        }
        while (given()
                .when().get(Endpoints.BOARDS + randomId).getStatusCode() != HttpStatus.SC_FORBIDDEN || tries == 50);

        return given()
                .when().get(Endpoints.BOARDS + randomId).jsonPath().get();
    }

    public Map<String, String> getBoardNotFound() {
        Random rand = new Random();
        int randomId;
        int tries = 0;

        do {
            randomId = rand.nextInt(3000);
            tries++;
        }
        while (givenAuthorized()
                .when().get(Endpoints.BOARDS + randomId).getStatusCode() != HttpStatus.SC_NOT_FOUND || tries == 50);

        return givenAuthorized()
                .when().get(Endpoints.BOARDS + randomId).jsonPath().get();
    }

    public boolean checkStatusMessage(int status) {
        return switch (status) {
            case 400 -> getBoardNotAssignedUser().get("error_message").equals("User has no access to board");
            case 403 -> getBoardNotAuthorized().get("error_message").equals("Access Denied");
            case 404 -> getBoardNotFound().get("error_message").equals("Board not found");
            default -> false;
        };
    }

    /*public boolean checkResponses(int id) {
        if (givenAuthorized()
                .when().get(Endpoints.BOARDS + id).getStatusCode() != HttpStatus.SC_OK) {
            System.err.println("Error in response");
            return false;
        }
        return true;
    }*/

    public List<Integer> getBoardsIds() {
        return getBoardsOfUser().jsonPath().getJsonObject("id");
    }

    public Response getBoardResponse(int id) {
        return givenAuthorized()
                .when().get(Endpoints.BOARDS + id);
    }

    public Map<String, String> getBoard(int id) {
        return getBoardResponse(id).jsonPath().getJsonObject("board");
    }

    public ArrayList<Map<String, String>> getColumns(int id) {
        return getBoardResponse(id).jsonPath().getJsonObject("columns");
    }

    public ArrayList<Map<String, String>> getUsers(int id) {
        return getBoardResponse(id).jsonPath().getJsonObject("users");
    }

    public List<String> getKeysToCompare(String filter) {
        switch (filter) {
            case "board":
                return Arrays.asList("id", "state", "name", "numberOfVotes");
            case "columns":
                return Arrays.asList("name", "id", "position", "colour");
            case "users":
                return Arrays.asList("email", "id");
            default:
                System.err.println("Wrong keys provided");
                return null;
        }
    }

    public boolean checkBoardKeys(int id) {
        List<String> keys = List.copyOf(getBoard(id).keySet());

        if (!keys.equals(getKeysToCompare("board"))) {
            System.err.println("Error in keys of board");
            return false;
        }
        return true;
    }

    public boolean checkColumnsKeys(int id) {
        for (Map<String, String> column : getColumns(id)) {
            List<String> keys = List.copyOf(column.keySet());

            if (!keys.equals(getKeysToCompare("columns"))) {
                System.err.print("Error in keys of columns");
                return false;
            }
        }
        return true;
    }

    public boolean checkUsersKeys(int id) {
        for (Map<String, String> user : getUsers(id)) {
            List<String> keys = List.copyOf(user.keySet());

            if (!keys.equals(getKeysToCompare("users"))) {
                System.err.print("Error in keys of users");
                return false;
            }
        }
        return true;
    }

    public boolean checkBoardsKeys() {
        for (int id : getBoardsIds()) {
            if (!(checkBoardKeys(id) && checkColumnsKeys(id) && checkUsersKeys(id)/* && checkResponses(id)*/)) {
                System.err.println("Error in keys of board " + id);
                return false;
            }
        }
        return true;
    }

    @Test
    public void checkAllBoardsOfUserDisplayingKeys() {
        assertThat(checkBoardsKeys(), equalTo(true));
    }

    @Test
    public void checkBoardUserNotAssigned() {
        assertThat(checkStatusMessage(400), equalTo(true));
    }

    @Test
    public void checkBoardUserNotAuthorized() {
        assertThat(checkStatusMessage(403), equalTo(true));
    }

    @Test
    public void checkBoardNotFound() {
        assertThat(checkStatusMessage(404), equalTo(true));
    }
}
