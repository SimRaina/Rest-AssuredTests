package org.test.legacyTests.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;        

/* Basic Authentication
Digest Authentication
Bearer Token (OAuth 2.0)
API Key Authentication
OAuth 1.0 Authentication */

public class AuthenticationTypesTest {

    private static final String BASE_URL = "https://api.example.com";

    // Basic Authentication
    public Response basicAuthTest(String username, String password) {
        return RestAssured
                .given()
                .auth()
                .basic(username, password)
                .when()
                .get(BASE_URL + "/basic-auth-endpoint");
    }

    // Digest Authentication
    public Response digestAuthTest(String username, String password) {
        return RestAssured
                .given()
                .auth()
                .digest(username, password)
                .when()
                .get(BASE_URL + "/digest-auth-endpoint");
    }

    // Bearer Token (OAuth 2.0)
    public Response bearerTokenAuthTest(String token) {
        return RestAssured
                .given()
                .auth()
                .oauth2(token)
                .when()
                .get(BASE_URL + "/bearer-token-endpoint");
    }

    // API Key Authentication
    public Response apiKeyAuthTest(String apiKey) {
        return RestAssured
                .given()
                .header("x-api-key", apiKey)
                .when()
                .get(BASE_URL + "/api-key-endpoint");
    }

    // OAuth 1.0 Authentication
    public Response oauth1AuthTest(String consumerKey, String consumerSecret, String accessToken, String tokenSecret) {
        return RestAssured
                .given()
                .auth()
                .oauth(consumerKey, consumerSecret, accessToken, tokenSecret)
                .when()
                .get(BASE_URL + "/oauth1-endpoint");
    }
}
