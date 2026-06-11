package org.test.legacyTests.testCases;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.test.configReader.ConfigReader;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.util.List;
import java.util.Map;
import static io.restassured.RestAssured.given;

@Listeners(org.test.Listener.ListenerTest.class)
public class GET_Typicode_AllUsers {
	
	static String host = ConfigReader.getValueFromPropertyFile("Typicode_Host");

    @Test
    public static void getTypicodeAllUsers() {
        Response response = 
        		given().
                when().get(host+"/users").  // getting all the user/ users list
                then().contentType(ContentType.JSON).   // check response Content-Type as JSON
                log().all().
                extract().response(); 
        
        String contentType = response.getContentType();
        System.out.println("Content-Type is: " + contentType);
        
        List<String> jsonResponse = response.jsonPath().getList("$"); // $ is indicating the root of the response JSON body
        
        System.out.println("Response is:"+jsonResponse);
        System.out.println("Number of User Records:"+jsonResponse.size());
        
        String usernames = response.jsonPath().getString("username");
        System.out.println(usernames);
        String usernames0 = response.jsonPath().getString("username[0]");
        System.out.println(usernames0);
        
        Map<String, String> company = response.jsonPath().getMap("company[0]");
        System.out.println(company.get("name")); // get to read the value of the key and put to insert the value in the map
        System.out.println(company.get("catchPhrase"));
        System.out.println(company.get("bs"));
        
        Assert.assertEquals(jsonResponse.size(), 10);

    }
}