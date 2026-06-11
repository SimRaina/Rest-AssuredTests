package org.test.legacyTests.HTTPMethods;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import org.test.configReader.ConfigReader;

import static io.restassured.RestAssured.*;

public class DELETE_Test {
	
	String host = ConfigReader.getValueFromPropertyFile("DummyRest_Host");
	
	@Test
	public void testDelete() {
		
		Response response =
		given()
		.when()
		  .delete(host+"/api/v1/delete/2")
		.then().statusCode(200)
			   .statusLine("HTTP/1.1 200 OK").log().all()
		       .extract().response();
		
		String responseInStringFormat = response.asString();  // convert Response type variable to String
		Assert.assertEquals(responseInStringFormat.contains("success"), true); // TestNG assertion
	}

}
