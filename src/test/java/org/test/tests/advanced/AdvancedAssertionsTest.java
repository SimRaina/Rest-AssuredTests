package org.test.tests.advanced;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

/**
 * Advanced assertion patterns and best practices
 * Consolidated from: AssertionsTest
 */
public class AdvancedAssertionsTest {

    private static final String BASE_URI = "https://jsonplaceholder.typicode.com";

    // ===========================
    // Basic Field Assertions
    // ===========================

    @Test(groups = {"assertions", "smoke"}, description = "Validate single field with equalTo matcher")
    public void testBasicFieldAssertion() {
        given()
            .baseUri(BASE_URI)
        .when()
            .get("/posts/1")
        .then()
            .statusCode(200)
            .body("id", equalTo(1));
    }

    @Test(groups = {"assertions"}, description = "Validate nested JSON field")
    public void testNestedFieldAssertion() {
        // Assuming API returns nested user object
        given()
            .baseUri(BASE_URI)
        .when()
            .get("/posts/1")
        .then()
            .statusCode(200)
            .body("title", notNullValue())
            .body("userId", greaterThan(0));
    }

    // ===========================
    // String Assertions
    // ===========================

    @Test(groups = {"assertions"}, description = "Validate string contains specific substring")
    public void testStringContainsAssertion() {
        given()
            .baseUri(BASE_URI)
        .when()
            .get("/posts/1")
        .then()
            .statusCode(200)
            .body("body", containsString("sunt aut"));
    }

    @Test(groups = {"assertions"}, description = "Validate multiple string conditions")
    public void testMultipleStringConditions() {
        given()
            .baseUri(BASE_URI)
        .when()
            .get("/users/1")
        .then()
            .statusCode(200)
            .body("email", containsString("@"))
            .body("phone", not(nullValue()))
            .body("website", notNullValue());
    }

    // ===========================
    // Numeric Assertions
    // ===========================

    @Test(groups = {"assertions"}, description = "Validate numeric field conditions")
    public void testNumericAssertions() {
        given()
            .baseUri(BASE_URI)
        .when()
            .get("/posts/1")
        .then()
            .statusCode(200)
            .body("userId", equalTo(1))
            .body("id", greaterThan(0))
            .body("id", lessThanOrEqualTo(100));
    }

    // ===========================
    // Collection Assertions
    // ===========================

    @Test(groups = {"assertions"}, description = "Validate array size")
    public void testArraySizeAssertion() {
        given()
            .baseUri(BASE_URI)
        .when()
            .get("/posts")
        .then()
            .statusCode(200)
            .body("size()", greaterThan(0))
            .body("size()", lessThanOrEqualTo(100));
    }

    @Test(groups = {"assertions"}, description = "Validate array contains specific items")
    public void testArrayContainsItems() {
        given()
            .baseUri(BASE_URI)
        .when()
            .get("/posts")
        .then()
            .statusCode(200)
            .body("id", hasItems(1, 2, 3));
    }

    @Test(groups = {"assertions"}, description = "Validate array element properties")
    public void testArrayElementProperties() {
        given()
            .baseUri(BASE_URI)
        .when()
            .get("/posts")
        .then()
            .statusCode(200)
            .body("userId", everyItem(greaterThan(0)))
            .body("id", everyItem(notNullValue()));
    }

    // ===========================
    // Combined Assertions
    // ===========================

    @Test(groups = {"assertions"}, description = "Validate multiple conditions with allOf")
    public void testCombinedConditionsWithAllOf() {
        given()
            .baseUri(BASE_URI)
        .when()
            .get("/posts/1")
        .then()
            .statusCode(200)
            .body("id", allOf(greaterThan(0), lessThan(100)))
            .body("userId", allOf(equalTo(1), greaterThan(0)));
    }

    @Test(groups = {"assertions"}, description = "Validate any matching condition with anyOf")
    public void testAnyOfCondition() {
        given()
            .baseUri(BASE_URI)
        .when()
            .get("/posts/1")
        .then()
            .statusCode(200)
            .body("userId", anyOf(equalTo(1), equalTo(2), equalTo(3)));
    }

    // ===========================
    // Key Existence Assertions
    // ===========================

    @Test(groups = {"assertions"}, description = "Verify JSON object contains specific keys")
    public void testKeyExistenceAssertion() {
        given()
            .baseUri(BASE_URI)
        .when()
            .get("/users/1")
        .then()
            .statusCode(200)
            .body("$", hasKey("id"))
            .body("$", hasKey("name"))
            .body("$", hasKey("email"));
    }

    // ===========================
    // Response Extraction and Assertion
    // ===========================

    @Test(groups = {"assertions"}, description = "Extract response values and assert separately")
    public void testResponseExtractionAndAssert() {
        Response response = given()
                .baseUri(BASE_URI)
            .when()
                .get("/posts/1")
            .then()
                .statusCode(200)
                .extract()
                .response();

        // Extract specific fields
        Integer postId = response.path("id");
        Integer userId = response.path("userId");
        String title = response.path("title");

        // Verify extracted values
        assertEquals(postId, 1, "Post ID should be 1");
        assertEquals(userId, 1, "User ID should be 1");
        assertNotNull(title, "Title should not be null");
        assertTrue(title.length() > 0, "Title should not be empty");
    }

    @Test(groups = {"assertions"}, description = "Extract collection and verify properties")
    public void testExtractCollectionAndAssert() {
        Response response = given()
                .baseUri(BASE_URI)
            .when()
                .get("/posts")
            .then()
                .statusCode(200)
                .extract()
                .response();

        // Get list size
        Integer size = response.path("size()");
        assertTrue(size > 0, "Should have posts");
        assertTrue(size <= 100, "Should not exceed 100 posts");
    }

    // ===========================
    // Status and Header Assertions
    // ===========================

    @Test(groups = {"assertions"}, description = "Validate status code and status line")
    public void testStatusCodeAndLine() {
        given()
            .baseUri(BASE_URI)
        .when()
            .get("/posts/1")
        .then()
            .statusCode(200)
            .statusLine(containsString("200"));
    }

    @Test(groups = {"assertions"}, description = "Validate response headers")
    public void testHeaderAssertions() {
        given()
            .baseUri(BASE_URI)
        .when()
            .get("/posts/1")
        .then()
            .statusCode(200)
            .header("Content-Type", containsString("json"))
            .header("Server", notNullValue());
    }
}

