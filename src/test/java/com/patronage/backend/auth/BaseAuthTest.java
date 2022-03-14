package com.patronage.backend.auth;

import com.patronage.backend.BaseTest;
import com.patronage.backend.Endpoints;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpHeaders.AUTHORIZATION;

public class BaseAuthTest extends BaseTest {

    private static final String EMAIL = System.getenv("USER_EMAIL");
    private static final String PASSWORD = System.getenv("USER_PASSWORD");

    private static String authToken;

    @BeforeClass
    public static void setupAuthToken() {
        if (authToken == null) {
            authToken = getAuthorizationHeader(EMAIL, PASSWORD);
        }
    }

    public RequestSpecification givenAuthorized() {
        return given().header(AUTHORIZATION, authToken);
    }

    private static String getAuthorizationHeader(String email, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
        return given().when().queryParams(params).post(Endpoints.LOGIN).getHeader(AUTHORIZATION);
    }
}
