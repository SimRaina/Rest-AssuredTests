package org.test.tests.api;

import org.test.base.APIConfig;
import org.test.base.BaseTest;
import org.test.base.TestDataProvider;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/**
 * Tests for Books API - Basic HTTP methods (GET, POST, PUT, DELETE)
 * Consolidated from: GET_Test, POST_Test, PUT_Test, DELETE_Test, BooksAPIe2eTest
 */
public class BooksAPITest extends BaseTest {

    private static final String ISBN = "100";
    private static final String UPDATED_ISBN = "103";

    @BeforeClass
    public void setupBooksAPI() {
        setBaseURI(APIConfig.BOOKS_API_HOST);
    }

    // ===========================
    // GET Tests
    // ===========================

    @Test(groups = {"get", "smoke"}, description = "Get all books - status code validation")
    public void testGetAllBooks_StatusCode() {
        given()
        .when()
            .get(APIConfig.BOOKS_ALL)
        .then()
            .statusCode(200);
    }

    @Test(groups = {"get", "smoke"}, description = "Get all books - response validation")
    public void testGetAllBooks_Response() {
        given()
        .when()
            .get(APIConfig.BOOKS_ALL)
        .then()
            .statusCode(200)
            .statusLine("HTTP/1.0 200 OK")
            .body("books[0].name", equalTo("Test Book 1"))
            .header("Content-Type", APIConfig.CONTENT_TYPE_JSON)
            .log().all();
    }

    @Test(groups = {"get"}, description = "Get book by ISBN")
    public void testGetBookByISBN() {
        given()
        .when()
            .get(APIConfig.BOOKS_BY_ISBN.replace("{isbn}", ISBN))
        .then()
            .statusCode(200)
            .statusLine("HTTP/1.0 200 OK")
            .body("name", equalTo("Test Book 1"))
            .body("price", equalTo(299))
            .header("Content-Type", APIConfig.CONTENT_TYPE_JSON)
            .log().all();
    }

    // ===========================
    // POST Tests
    // ===========================

    @Test(groups = {"post"}, description = "Create new book")
    public void testCreateBook() {
        Map<String, String> requestBody = TestDataProvider.getDefaultEmployeeData();

        given()
            .contentType(APIConfig.CONTENT_TYPE_JSON)
            .body(requestBody)
        .when()
            .post(APIConfig.BOOKS_CREATE)
        .then()
            .statusCode(200)
            .log().all();
    }

    // ===========================
    // PUT Tests
    // ===========================

    @Test(groups = {"put"}, description = "Update book by ISBN")
    public void testUpdateBook() {
        Map<String, String> requestBody = TestDataProvider.getDefaultEmployeeData();

        given()
            .contentType(APIConfig.CONTENT_TYPE_JSON)
            .body(requestBody)
        .when()
            .put(APIConfig.BOOKS_BY_ISBN.replace("{isbn}", UPDATED_ISBN))
        .then()
            .statusCode(200)
            .log().all();
    }

    // ===========================
    // DELETE Tests
    // ===========================

    @Test(groups = {"delete"}, description = "Delete book by ISBN")
    public void testDeleteBook() {
        given()
        .when()
            .delete(APIConfig.BOOKS_BY_ISBN.replace("{isbn}", UPDATED_ISBN))
        .then()
            .statusCode(200)
            .log().all();
    }
}

