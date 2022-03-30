package com.patronage.backend.users;

import com.patronage.backend.Endpoints;
import com.patronage.backend.auth.BaseAuthTest;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpHeaders.AUTHORIZATION;

public class UsersTest extends BaseAuthTest {

    @Test
    public void postAuthorizationDataShouldReturnToken() {

        Map<String, String> params = new HashMap<>();
        params.put("email", System.getenv("USER_EMAIL"));
        params.put("password", System.getenv("USER_PASSWORD"));
        String token = given().when().queryParams(params).post(Endpoints.LOGIN).getHeader(AUTHORIZATION);

        Assert.assertNotNull(token);
    }
}
