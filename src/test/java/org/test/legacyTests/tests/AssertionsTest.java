package org.test.legacyTests.tests;

import org.testng.annotations.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class AssertionsTest {
    
    @Test
    public void test1() {
        given()
        .when().get("/endpoint")
        .then()
            .statusCode(200) // Validate HTTP status code
            .body("message", equalTo("Success")) // Validate JSON field value
            .body("user.id", equalTo(101)) // Validate nested JSON field
            .body("user.email", containsString("@gmail.com")) // Validate string contains
            .body("user.age", greaterThan(18)) // Validate number condition
            .body("user.salary", lessThanOrEqualTo(100000)); // Validate number is within range
    }

    @Test
    public void test2() {

        // If the response contains an array, you can verify its elements.
        given()
        .when().get("/endpoint")
        .then()
            .body("users.size()", equalTo(3)) // Validate array size
            .body("users[0].name", equalTo("John Doe")) // Validate first object in array
            .body("users.name", hasItems("John Doe", "Jane Doe")) // Validate multiple values exist in array
            .body("users.age", hasItem(30)); // Validate specific number in array
    }

    @Test
    public void test3() {
       
       // You can combine multiple conditions using Hamcrest matchers.
    given()
        .when().get("/endpoint")
        .then()
            .body("status", anyOf(equalTo("active"), equalTo("pending"))) // Match multiple values
            .body("users.size()", allOf(greaterThan(1), lessThan(5))) // Multiple conditions
            .body("users.id", everyItem(greaterThan(0))); // Validate all elements in an array
    }

    @Test
    public void test4() {
        // To check if a JSON object contains a specific key:
        given()
        .when().get("/endpoint")
        .then()
            .body("user", hasKey("name")) // Check if key exists
            .body("user", hasKey("email"))
            .body("metadata", hasKey("timestamp")); // Validate nested key existence
    }   

    @Test
    public void test5() {
        // To validate the entire response structure against a predefined JSON Schema:
        given()
        .when().get("/endpoint")
        .then()
            .body(matchesJsonSchemaInClasspath("schema.json")); // Validate against JSON Schema
        // Place schema.json inside src/test/resources/ for easy access.
    }
    
    @Test
    public void test6() {
     //If you need to store response values and assert them later:

        Response response = given()
            .when().get("/endpoint")
            .then()
                .statusCode(200)
                .extract().response();

        // Validate extracted values
        String name = response.path("user.name");
        int age = response.path("user.age");

        assertThat(name, equalTo("John Doe"));
        assertThat(age, greaterThan(18));
    }
}
