package org.test.legacyTests.tests;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import org.test.configReader.ConfigReader;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@Listeners(org.test.Listener.ListenerTest.class)
public class Test1_XML_GET {
	
	String host = ConfigReader.getValueFromPropertyFile("ThomasBayer_Host");
	
	@Test
	public void testSingleContent() {
		given().
		get(host+"/sqlrest/CUSTOMER/10").
		then().
		body("CUSTOMER.ID", equalTo("10")).
		log().all();
	}
	
	@Test
	public void testMultipleContent() {
		given().
			get(host+"/sqlrest/CUSTOMER/10").
		then().
			body("CUSTOMER.ID", equalTo("10")).
			body("CUSTOMER.FIRSTNAME", equalTo("Sue")).
			body("CUSTOMER.LASTNAME", equalTo("Fuller")).body("CUSTOMER.STREET", equalTo("135 Upland Pl.")).
			body("CUSTOMER.CITY", equalTo("Dallas")).
			log().all();
		}
		
	@Test
	public void testCompleteTextInOneGo() {
		given().
			get(host+"/sqlrest/CUSTOMER/10").
		then().
			body("CUSTOMER.text()", equalTo("10SueFuller135 Upland Pl.Dallas")).
			log().all();
	}
		
	@Test
	public void testUsingXpath() {
		given().
			get(host+"/sqlrest/CUSTOMER/10").
		then().
			body(hasXPath("/CUSTOMER/FIRSTNAME", containsString("Sue")));
	}
		
	@Test
	public void testXpath2() {
		given().
		get(host+"/sqlrest/INVOICE/").
		then().
		body(hasXPath("/INVOICEList/INVOICE[text()='40']")).log().all();
	}
}
