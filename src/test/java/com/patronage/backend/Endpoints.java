package com.patronage.backend;

import io.restassured.RestAssured;
import org.junit.BeforeClass;

public class Endpoints {

    public static String LOGIN = "v1/login";
    public static String BOARDS = "v1/boards";

    @BeforeClass
    public static void setupURI() {
        RestAssured.baseURI = "https://patronage22-szczecin-java.herokuapp.com/";
        RestAssured.basePath = "api/";
    }
}
