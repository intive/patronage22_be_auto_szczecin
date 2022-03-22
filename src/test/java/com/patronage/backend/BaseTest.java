package com.patronage.backend;

import io.restassured.RestAssured;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;

public class BaseTest extends Endpoints {

    @Rule
    public TestName testName = new TestName();

    @BeforeClass
    public static void setupRestAssuredLogging() {
        RestAssured.filters(
                new RequestLoggingFilter(),
                new ResponseLoggingFilter(),
                new ErrorLoggingFilter()
        );
    }

    @Before
    public void logTestName() {
        System.out.println("--------------------------------------------------");
        System.out.println("RUNNING TEST:");
        System.out.println(testName.getMethodName());
        System.out.println("--------------------------------------------------");
    }
}
