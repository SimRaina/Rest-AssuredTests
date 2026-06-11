package org.test.legacyTests.testCases;


import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import org.test.configReader.ConfigReader;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@Listeners(org.test.Listener.ListenerTest.class)
public class GET_ErgastCircuits_Test {

	
	String host = ConfigReader.getValueFromPropertyFile("Ergast_Host");
	
	@Test
	public void getErgastCircuitsTest() {
		
		given().when().get(host+"/api/f1/2017/circuits.json").
		then().
		assertThat(). // readability
		statusCode(200).
		and().
		body("MRData.CircuitTable.Circuits.circuitid", hasSize(20)).  // check response body and asserting that count of circuitids to be 20
		and().
		//body("MRData.total", equalTo("20")).
		header("content-length", equalTo("4551")).and().log().all(); // check response header
	}
}
