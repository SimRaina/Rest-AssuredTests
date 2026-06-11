package org.test.legacyTests.tests;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import org.test.configReader.ConfigReader;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@Listeners(org.test.Listener.ListenerTest.class)
public class Test1_JSON_GET {
	
	String host = ConfigReader.getValueFromPropertyFile("Typicode_Host");
	String host1 = ConfigReader.getValueFromPropertyFile("Ergast_Host");
	
	@Test
	public void testStatusCode() {
		given().
		get(host+"/posts/3").
		then().
		statusCode(200);
	}

	@Test
	public void testLogging() {
		given().
		get(host+"/posts/3").
		then().
		log().all();
	}

	@Test
	public void testEqualToFunction() {
		given().
		get(host+"/posts/3").
		then().
		body("id", equalTo(3));
	}
	
	@Test
	public void testHasItemFunction() {
		given().
		get(host+"/posts").
		then().
		body("id", hasItems(3,2,100));
	}

	@Test
	public void testParametersandHeaders() {
		given().
		param("Key1","Value1").
		header("HeaderKey1","HeaderValue1").
		when().
		get(host+"/posts/3").
		then().
		statusCode(200).   
		log().all();
	}
	
	@Test
	public void testParametersandHeadersWithAnd() {
		given().param("Key1","Value1").and().header("HeaderKey1","HeaderValue1").
		when().get(host+"/posts/3").then().statusCode(200).and().body("id", equalTo(3));
	}

	@Test
	public void testWithoutRoot() {
		given().
		get(host1+"/api/f1/2017/circuits.json").
		then().
		body("MRData.CircuitTable.Circuits[0].Location.locality", is("Melbourne")).
		log().all();
	}

	@Test
	public void testWithRoot() {
		given().
		get(host1+"/api/f1/2017/circuits.json").
		then().
		rootPath("MRData.CircuitTable").
		body("Circuits[0].Location.locality", is("Melbourne"));
	}
}
