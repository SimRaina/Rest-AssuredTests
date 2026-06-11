package org.test.base;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

/**
 * Helper class for common API operations
 */
public class APIHelper {

    /**
     * Perform GET request
     */
    public static Response get(String endpoint) {
        return given()
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }

    /**
     * Perform GET request with base URI
     */
    public static Response getWithBaseUri(String baseUri, String endpoint) {
        return given()
                .baseUri(baseUri)
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }

    /**
     * Perform POST request with body
     */
    public static Response post(String endpoint, Object body) {
        return given()
                .contentType("application/json")
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();
    }

    /**
     * Perform POST request with body and base URI
     */
    public static Response postWithBaseUri(String baseUri, String endpoint, Object body) {
        return given()
                .baseUri(baseUri)
                .contentType("application/json")
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();
    }

    /**
     * Perform PUT request with body
     */
    public static Response put(String endpoint, Object body) {
        return given()
                .contentType("application/json")
                .body(body)
                .when()
                .put(endpoint)
                .then()
                .extract()
                .response();
    }

    /**
     * Perform PUT request with body and base URI
     */
    public static Response putWithBaseUri(String baseUri, String endpoint, Object body) {
        return given()
                .baseUri(baseUri)
                .contentType("application/json")
                .body(body)
                .when()
                .put(endpoint)
                .then()
                .extract()
                .response();
    }

    /**
     * Perform DELETE request
     */
    public static Response delete(String endpoint) {
        return given()
                .when()
                .delete(endpoint)
                .then()
                .extract()
                .response();
    }

    /**
     * Perform DELETE request with base URI
     */
    public static Response deleteWithBaseUri(String baseUri, String endpoint) {
        return given()
                .baseUri(baseUri)
                .when()
                .delete(endpoint)
                .then()
                .extract()
                .response();
    }
}

