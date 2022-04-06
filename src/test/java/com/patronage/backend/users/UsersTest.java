package com.patronage.backend.users;

import com.patronage.backend.Endpoints;
import com.patronage.backend.auth.BaseAuthTest;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpHeaders.AUTHORIZATION;
import static org.hamcrest.Matchers.notNullValue;

public class UsersTest extends BaseAuthTest {

    private String getAuthorizationToken() {
        Map<String, String> params = new HashMap<>();
        params.put("email", System.getenv("USER_EMAIL"));
        params.put("password", System.getenv("USER_PASSWORD"));
        return given().when().queryParams(params).post(Endpoints.LOGIN).getHeader(AUTHORIZATION);
    }

    @Test
    public void postAuthorizationDataShouldReturnToken() {
        Assert.assertNotNull(getAuthorizationToken());
    }

    @Test
    public void postCreatingBoardRequestShouldReturnStatusCode201() {
        RequestSpecification header = given().header(AUTHORIZATION, getAuthorizationToken());
        givenBodyJson(header, "{\"name\": \"name77\"}")
                .when().post(Endpoints.BOARDS)
                .then().statusCode(HttpStatus.SC_CREATED);
    }

    @Test
    public void postCreatingBoardRequestShouldReturnID() {
        RequestSpecification header = given().header(AUTHORIZATION, getAuthorizationToken());
        givenBodyJson(header, "{\"name\": \"name77\"}")
                .when().post(Endpoints.BOARDS)
                .then().body("id", notNullValue());
    }
}
