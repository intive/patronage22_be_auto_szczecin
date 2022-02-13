package com.patronage.backend;


import io.restassured.RestAssured;
import org.junit.BeforeClass;

public class Endpoints {

    @BeforeClass
    public static void setupURI() {
        RestAssured.baseURI = "https://www.boredapi.com";
        RestAssured.basePath = "/api";
    }
}
