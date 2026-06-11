package org.test.tests.api;

import org.test.base.APIConfig;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/**
 * Tests for External Public APIs
 * Consolidated from: Test1_JSON_GET, GET_ExcelData_Test, Test1_XML_GET, GET_ThomasBayer_XML, etc.
 */
public class ExternalAPIsTest {

    // ===========================
    // Typicode JSON Tests
    // ===========================

    @Test(groups = {"typicode", "smoke"}, description = "Get post by ID from Typicode")
    public void testGetTypicodePostById() {
        given()
            .baseUri(APIConfig.TYPICODE_HOST)
        .when()
            .get(APIConfig.POST_BY_ID.replace("{id}", "3"))
        .then()
            .statusCode(200)
            .body("id", equalTo(3));
    }

    @Test(groups = {"typicode"}, description = "Log Typicode post response")
    public void testTypicodePostLogging() {
        given()
            .baseUri(APIConfig.TYPICODE_HOST)
        .when()
            .get(APIConfig.POST_BY_ID.replace("{id}", "3"))
        .then()
            .log().all();
    }

    @Test(groups = {"typicode"}, description = "Get Typicode posts with parameters and headers")
    public void testTypicodeWithParametersAndHeaders() {
        given()
            .baseUri(APIConfig.TYPICODE_HOST)
            .param("Key1", "Value1")
            .header("HeaderKey1", "HeaderValue1")
        .when()
            .get(APIConfig.POST_BY_ID.replace("{id}", "3"))
        .then()
            .statusCode(200)
            .log().all();
    }

    @Test(groups = {"typicode"}, description = "Get multiple Typicode posts with hasItems matcher")
    public void testTypicodeMultiplePostsValidation() {
        given()
            .baseUri(APIConfig.TYPICODE_HOST)
        .when()
            .get(APIConfig.POSTS_ALL)
        .then()
            .body("id", hasItems(3, 2, 100));
    }

    @Test(groups = {"typicode"}, description = "Test Typicode with rootPath")
    public void testTypicodeWithRootPath() {
        given()
            .baseUri(APIConfig.TYPICODE_HOST)
        .when()
            .get(APIConfig.POSTS_ALL)
        .then()
            .body("size()", greaterThan(0))
            .log().all();
    }

    // ===========================
    // Ergast Formula 1 Tests
    // ===========================

    @Test(groups = {"ergast", "smoke"}, description = "Get F1 circuits in JSON format")
    public void testErgastCircuitsJSON() {
        given()
            .baseUri(APIConfig.ERGAST_HOST)
        .when()
            .get(APIConfig.ERGAST_CIRCUITS_JSON)
        .then()
            .statusCode(200)
            .body("MRData.CircuitTable.Circuits[0].Location.locality", is("Melbourne"))
            .log().all();
    }

    @Test(groups = {"ergast"}, description = "Get F1 circuits in XML format")
    public void testErgastCircuitsXML() {
        given()
            .baseUri(APIConfig.ERGAST_HOST)
        .when()
            .get(APIConfig.ERGAST_CIRCUITS_XML)
        .then()
            .statusCode(200)
            .header("Content-Type", APIConfig.CONTENT_TYPE_XML)
            .body("MRData.CircuitTable.Circuit[0].CircuitName", equalTo("Albert Park Grand Prix Circuit"))
            .body("MRData.CircuitTable.Circuit[0].Location.Locality", equalTo("Melbourne"))
            .log().all();
    }

    // ===========================
    // Reqres API Tests
    // ===========================

    @Test(groups = {"reqres"}, description = "Get user from Reqres")
    public void testReqresGetUser() {
        given()
            .baseUri("https://reqres.in")
        .when()
            .get("/api/users/2")
        .then()
            .statusCode(200)
            .body("data.id", equalTo(2))
            .log().all();
    }

    @Test(groups = {"reqres"}, description = "Create user on Reqres")
    public void testReqresCreateUser() {
        given()
            .baseUri("https://reqres.in")
            .body("{ \"name\": \"morpheus\", \"job\": \"leader\" }")
            .contentType("application/json")
        .when()
            .post("/api/users")
        .then()
            .statusCode(201)
            .log().all();
    }

    @Test(groups = {"reqres"}, description = "Update user on Reqres")
    public void testReqresUpdateUser() {
        given()
            .baseUri("https://reqres.in")
            .body("{ \"name\": \"morpheus\", \"job\": \"zion resident\" }")
            .contentType("application/json")
        .when()
            .put("/api/users/2")
        .then()
            .statusCode(200)
            .body("name", equalTo("morpheus"))
            .log().all();
    }

    @Test(groups = {"reqres"}, description = "Delete user from Reqres")
    public void testReqresDeleteUser() {
        given()
            .baseUri("https://reqres.in")
        .when()
            .delete("/api/users/2")
        .then()
            .statusCode(204)
            .log().all();
    }

    // ===========================
    // Thomas Bayer API Tests
    // ===========================

    @Test(groups = {"thomasbayer"}, description = "Get invoice data from Thomas Bayer API")
    public void testThomasBayerGetInvoice() {
        given()
            .baseUri(APIConfig.THOMAS_BAYER_HOST)
        .when()
            .get(APIConfig.THOMAS_BAYER_INVOICE)
        .then()
            .statusCode(200)
            .log().all();
    }
}

