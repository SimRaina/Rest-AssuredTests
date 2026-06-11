package org.test.legacyTests.tests;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;


public class ReadJSONFileTest {
    
    @Test
    public void readJsonFile() {
        try {
            // Read the JSON file as a String
            String jsonFilePath = "src/test/resources/request.json"; // Change path as needed
            String requestBody = new String(Files.readAllBytes(Paths.get(jsonFilePath)));

            // Define the Base URI
            RestAssured.baseURI = "https://your-api-endpoint.com";

            // Send the POST request
            Response response = RestAssured.given()
                    .header("Content-Type", "application/json")
                    .body(requestBody)
                    .post("/your-endpoint-path");

            // Print Response
            System.out.println("Response Code: " + response.getStatusCode());
            System.out.println("Response Body: " + response.getBody().asString());

        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

    @Test
    public void readJsonFileAlternative() {

        // Alternative Approach: Using JSON File Directly

        //You can also use Rest Assured’s .body(new File("path")) method to send the JSON file directly:

        File jsonFile = new File("src/test/resources/request.json");

        RestAssured.given()
            .header("Content-Type", "application/json")
            .body(jsonFile)
                .post("/your-endpoint-path");
                
        //This avoids reading the file manually and simplifies the code.
    }
}
