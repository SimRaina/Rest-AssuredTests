package org.test.legacyTests.HTTPMethods;

import org.testng.annotations.Test;

import org.test.configReader.ConfigReader;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class GET_Test {
	
	String host = ConfigReader.getValueFromPropertyFile("BooksAPI_Host");
	
	@Test
	public void getAllBooks() {
      given()
      .when()
          .get(host+"/books")  // http://127.0.0.1:5000/books
      .then()
          .statusCode(200).and()
          .statusLine("HTTP/1.0 200 OK")
          .body("books[0].name", equalTo("Test Book 1"))
          .header("Content-Type", "application/json") // check response header for Content-Type
          .log().all();	// print the response logs on the console
	}
	
	@Test
	public void getBookByISBN() {
      given()
      .when()
         .get(host+"/books/100") // http://127.0.0.1:5000/books
      .then()
         .statusCode(200)
         .statusLine("HTTP/1.0 200 OK")
         .body("name", equalTo("Test Book 1"))
         .body("price", equalTo(299))
         .header("Content-Type", "application/json")
         .log().all();	
	}
}
