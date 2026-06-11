package org.test.tests.advanced;

import io.restassured.response.Response;
import org.test.base.BaseTest;
import org.test.pojoClasses.PostPayLoad;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/**
 * POJO (Plain Old Java Object) serialization and deserialization tests
 * Consolidated from: TypicodeTest
 */
public class POJOSerializationTest extends BaseTest {

    @Test(groups = {"pojo", "smoke"}, description = "Serialize POJO to JSON and POST")
    public void testPostWithPOJO() {
        PostPayLoad payload = new PostPayLoad(
            22,
            22,
            "This is test",
            "This is the first test to create user"
        );

        Response response = given()
                .baseUri("https://jsonplaceholder.typicode.com")
                .contentType("application/json")
                .body(payload)
            .when()
                .post("/posts")
            .then()
                .statusCode(201)
                .extract()
                .response();

        System.out.println("Response is: " + response.asString());
    }

    @Test(groups = {"pojo"}, description = "POST POJO and validate response fields")
    public void testPostPOJOWithValidation() {
        PostPayLoad payload = new PostPayLoad(
            1,
            1,
            "Test Title",
            "Test Body"
        );

        given()
            .baseUri("https://jsonplaceholder.typicode.com")
            .contentType("application/json")
            .body(payload)
        .when()
            .post("/posts")
        .then()
            .statusCode(201)
            .body("userId", equalTo(1))
            .body("id", greaterThan(0))
            .log().all();
    }

    @Test(groups = {"pojo"}, description = "GET and deserialize JSON to POJO")
    public void testGetAndDeserializeToPOJO() {
        Response response = given()
                .baseUri("https://jsonplaceholder.typicode.com")
            .when()
                .get("/posts/1")
            .then()
                .statusCode(200)
                .extract()
                .response();

        PostPayLoad post = response.as(PostPayLoad.class);
        System.out.println("Title: " + post.getTitle());
        System.out.println("Body: " + post.getBody());
    }
}

