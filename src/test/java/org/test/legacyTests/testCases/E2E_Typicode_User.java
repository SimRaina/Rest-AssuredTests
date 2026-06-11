package org.test.legacyTests.testCases;

import static io.restassured.RestAssured.*;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.test.configReader.ConfigReader;

@Listeners(org.test.Listener.ListenerTest.class)
public class E2E_Typicode_User {
	
	@BeforeClass
    public void setup() {
        RestAssured.baseURI = ConfigReader.getValueFromPropertyFile("Typicode_Host");
    }

    @SuppressWarnings("unchecked")
	@Test
    public void e2eTypicodeUser() {

        JSONObject json = new JSONObject();
        json.put("userId", 22);
        json.put("id", 22);
        json.put("title", "This is test");
        json.put("body", "This is the first test to create user");

        Response response =
                given()
                    .contentType("application/json")
                    .body(json)
                .when()
                    .post("/posts")
                .then()
                    .statusCode(201)
                    .extract().response();

        System.out.println("Response is: " + response.asString());
    }
}

