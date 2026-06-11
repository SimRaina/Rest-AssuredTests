package org.test.legacyTests.testCases;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import org.test.configReader.ConfigReader;

import static io.restassured.RestAssured.*;

@Listeners(org.test.Listener.ListenerTest.class)
public class GET_EU_Test {
	
	String host = ConfigReader.getValueFromPropertyFile("EU_Host");
	
	@Test()
	public void getEUTest() {
		int expectedCode=200;
		
		Response response = get(host+"/rest/v1/name/norway");
		
		int responseCode=response.getStatusCode();
		
		long time=response.getTime(); // get the response time
		
		
		String data=response.asString(); // for printing purpose we are converting it to String
		 
		
		System.out.println("Status code is "+responseCode);
		System.out.println("Data is "+data);
		System.out.println("Response Time: "+time);
		
		Assert.assertEquals(responseCode,expectedCode); // TestNG Assertion
		Assert.assertTrue(time<3000); // less than 3000 ms / 3 seconds 
		
		
		/* given().when().get(host+"/rest/v1/name/norway").
		then().
		assertThat().
		statusCode(expectedCode).
		and().
		time(lessThan(3000L)).and().log().all(); // 3000 Long ms */
		 
	}
}



