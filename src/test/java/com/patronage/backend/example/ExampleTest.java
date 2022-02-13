package com.patronage.backend.example;
import com.patronage.backend.BaseTest;


import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.junit.BeforeClass;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ExampleTest extends BaseTest {

    private static ResponseSpecification responseSpec;

    @BeforeClass
    public static void setup() {
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectStatusCode(200);
        responseSpec = responseSpecBuilder.build();
    }

    @Test
    public void exampleTestPing(){
        given().when().get("/activity").then().statusCode(200);
    }

    @Test
    public void exampleTestBodyInfo(){
        given().
                when()
                .get("/activity")
                .then()
                .body(containsString("type"), is(notNullValue()))
                .body(containsString("activity"), is(notNullValue()));
    }

    @Test
    public void exampleTestWrongResponse(){
        given().
                when()
                .get("/activity")
                .then()
                .body("$",is(not(hasKey("wrong"))));
    }

    @Test
    public void exampleTestPreciseResponse(){
        given().
                when()
                .get("/activity?key=5881028")
                .then()
                .body("type", equalTo("education"))
                .body("participants", equalTo(1));
    }





}
