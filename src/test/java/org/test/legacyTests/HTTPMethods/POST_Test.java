package org.test.legacyTests.HTTPMethods;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import org.test.configReader.ConfigReader;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;


public class POST_Test {
	
	String host = ConfigReader.getValueFromPropertyFile("DummyRest_Host");
	
	public HashMap<String, String> map = new HashMap<>(); 
	
	String name = "Simran";
	String salary = "140000";
	String age = "30";

    @BeforeTest
	public void postTestData() { // run this explicitly when we are passing the global parameters inside the map
		
		map.put("name", name);
        map.put("salary", salary);
        map.put("age", age);
	}
	
	@Test
	public void testPost() {
		given()
		   .contentType("application/json")
		   .body(map)
		.when()
		   .post(host+"/api/v1/create")
		.then()
			.statusCode(200)
			.log().all()
			.body("message", equalTo("Successfully! Record has been added."))
			.body("status", equalTo("success"))
			.body("data.name", equalTo(name))
			.body("data.salary", equalTo(salary))
			.body("data.age", equalTo(age))
			.header("Content-Type", "application/json");	 
	}
}
