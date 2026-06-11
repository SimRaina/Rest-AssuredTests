package org.test.tests.api;

import org.test.base.APIConfig;
import org.test.base.BaseTest;
import org.test.base.TestDataProvider;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/**
 * Tests for DummyRest API - CRUD operations
 * Consolidated from: POST_Test, PUT_Test, DELETE_Test in HTTPMethods folder
 */
public class DummyRestAPITest extends BaseTest {

    private static final int TEST_EMPLOYEE_ID = 1;
    private static final int DELETE_EMPLOYEE_ID = 2;

    @BeforeClass
    public void setupDummyRestAPI() {
        setBaseURI(APIConfig.DUMMY_REST_HOST);
    }

    // ===========================
    // POST Tests
    // ===========================

    @Test(groups = {"post", "smoke"}, description = "Create new employee via POST")
    public void testCreateEmployee() {
        Map<String, String> employeeData = TestDataProvider.createEmployeeData("Simran", "140000", "30");

        given()
            .contentType(APIConfig.CONTENT_TYPE_JSON)
            .body(employeeData)
        .when()
            .post(APIConfig.DUMMY_CREATE_EMPLOYEE)
        .then()
            .statusCode(200)
            .body("message", equalTo("Successfully! Record has been added."))
            .body("status", equalTo("success"))
            .body("data.name", equalTo("Simran"))
            .body("data.salary", equalTo("140000"))
            .body("data.age", equalTo("30"))
            .header("Content-Type", APIConfig.CONTENT_TYPE_JSON)
            .log().all();
    }

    @Test(groups = {"post"}, description = "Create employee with custom data")
    public void testCreateEmployeeWithCustomData() {
        Map<String, String> employeeData = TestDataProvider.createEmployeeData("John Doe", "150000", "35");

        given()
            .contentType(APIConfig.CONTENT_TYPE_JSON)
            .body(employeeData)
        .when()
            .post(APIConfig.DUMMY_CREATE_EMPLOYEE)
        .then()
            .statusCode(200)
            .body("status", equalTo("success"))
            .log().all();
    }

    // ===========================
    // PUT Tests
    // ===========================

    @Test(groups = {"put"}, description = "Update employee via PUT")
    public void testUpdateEmployee() {
        Map<String, String> employeeData = TestDataProvider.createEmployeeData("Simran", "14000", "30");

        given()
            .contentType(APIConfig.CONTENT_TYPE_JSON)
            .header("AuthToken", "xlkjdjfkdlkksfkkdkl")
            .body(employeeData)
        .when()
            .put(APIConfig.DUMMY_UPDATE_EMPLOYEE.replace("{id}", String.valueOf(TEST_EMPLOYEE_ID)))
        .then()
            .statusCode(200)
            .body("message", equalTo("Successfully! Record has been updated."))
            .log().all();
    }

    @Test(groups = {"put"}, description = "Update employee with different salary")
    public void testUpdateEmployeeSalary() {
        Map<String, String> employeeData = TestDataProvider.createEmployeeData("Jane Doe", "160000", "28");

        given()
            .contentType(APIConfig.CONTENT_TYPE_JSON)
            .body(employeeData)
        .when()
            .put(APIConfig.DUMMY_UPDATE_EMPLOYEE.replace("{id}", String.valueOf(TEST_EMPLOYEE_ID)))
        .then()
            .statusCode(200)
            .log().all();
    }

    // ===========================
    // DELETE Tests
    // ===========================

    @Test(groups = {"delete"}, description = "Delete employee via DELETE")
    public void testDeleteEmployee() {
        var response = given()
        .when()
            .delete(APIConfig.DUMMY_DELETE_EMPLOYEE.replace("{id}", String.valueOf(DELETE_EMPLOYEE_ID)))
        .then()
            .statusCode(200)
            .statusLine("HTTP/1.1 200 OK")
            .log().all()
            .extract()
            .response();

        String responseBody = response.asString();
        Assert.assertTrue(responseBody.contains("success"), "Response should contain 'success'");
    }

    @Test(groups = {"delete"}, description = "Delete employee - verify success message")
    public void testDeleteEmployeeVerifyMessage() {
        given()
        .when()
            .delete(APIConfig.DUMMY_DELETE_EMPLOYEE.replace("{id}", String.valueOf(DELETE_EMPLOYEE_ID)))
        .then()
            .statusCode(200)
            .body(containsString("success"))
            .log().all();
    }
}

