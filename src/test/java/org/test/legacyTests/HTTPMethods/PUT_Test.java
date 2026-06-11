package org.test.legacyTests.HTTPMethods;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import org.test.configReader.ConfigReader;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

public class PUT_Test {
	
	String host = ConfigReader.getValueFromPropertyFile("DummyRest_Host");
	HashMap<String, String> map = new HashMap<>(); // not initialized
	
	@BeforeTest
	public void putData() {
		map.put("name", "Simran");
        map.put("salary", "14000");
        map.put("age","30");
	}
	
	@Test
	public void testPut() {
		given()
		      .contentType("application/json")
		      .header("AuthToken", "xlkjdjfkdlkksfkkdkl")
		      .body(map)
		.when()
		      .put(host+"/api/v1/update/1")
		.then()
		      .statusCode(200)
		      .body("message", equalTo("Successfully! Record has been updated."))
		      .log().all();
	}
}
