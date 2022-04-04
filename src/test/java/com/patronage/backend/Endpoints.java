package com.patronage.backend;

import io.restassured.RestAssured;
import org.junit.BeforeClass;

public class Endpoints {

    public static String LOGIN = "v1/login";
    public static String BOARDS = "v1/boards/";

    @BeforeClass
    public static void setupURI() {
        RestAssured.baseURI = "https://intive-java-szczecin-2022.herokuapp.com/";
        RestAssured.basePath = "api/";
    }
}
