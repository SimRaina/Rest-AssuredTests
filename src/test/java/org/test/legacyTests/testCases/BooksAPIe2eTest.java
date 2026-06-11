package org.test.legacyTests.testCases;

import org.test.ExcelReader.ExcelReaderTest;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class BooksAPIe2eTest {

    String filepath = System.getProperty("user.dir") + "\\Resources\\Data\\";
    ArrayList<String> data = new ArrayList<String>();
    ExcelReaderTest excel;
    String url = "http://127.0.0.1:5000";
    String uri;
    String contentType;
    String request;
    String isbn = "/103";

    @Test
    public void getBooksAPI(){

        excel = new ExcelReaderTest();
        data = excel.getData(filepath, "testData.xlsx", "e2eData", 1);
        uri = data.get(1);

        given().
        when().get(url+uri).
        then().log().all();
    }

    @Test(dependsOnMethods = "getBooksAPI")
    public void postBooksAPI(){

        excel  = new ExcelReaderTest();
        data = excel.getData(filepath, "testData.xlsx", "e2eData", 2);
        uri = data.get(1);
        contentType = data.get(2);
        request = data.get(3);

        given().contentType(contentType).body(request).
        when().post(url+uri).
        then().log().all();
    }

    @Test(dependsOnMethods = "postBooksAPI")
    public void putBooksAPI(){

        excel  = new ExcelReaderTest();
        data = excel.getData(filepath, "testData.xlsx", "e2eData", 3);
        uri = data.get(1);
        contentType = data.get(2);
        request = data.get(3);

        given().contentType(contentType).body(request).
        when().put(url+uri+isbn).
        then().log().all();
    }

    @Test(dependsOnMethods = "putBooksAPI")
    public void deleteBooksAPI(){

        excel  = new ExcelReaderTest();
        data = excel.getData(filepath, "testData.xlsx", "e2eData", 4);
        uri = data.get(1);

        given().
        when().delete(url+uri+isbn).
        then().log().all();
    }
}
