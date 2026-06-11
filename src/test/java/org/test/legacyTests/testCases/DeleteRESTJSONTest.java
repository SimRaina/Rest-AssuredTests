package org.test.legacyTests.testCases;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DeleteRESTJSONTest {


    @Test
    public void deleteRESTJSONTest(){

        given().header("mytest", "header").
        when().delete("https://reqres.in/api/users/6").
        then().statusCode(204).log().all();
    }
}
