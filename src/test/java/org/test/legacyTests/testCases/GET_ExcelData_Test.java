package org.test.legacyTests.testCases;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import java.util.ArrayList;
import io.restassured.response.*;
import org.test.configReader.ConfigReader;
import org.test.ExcelReader.*;;

@Listeners(org.test.Listener.ListenerTest.class)
public class GET_ExcelData_Test {
	
	ExcelReaderTest excel;
	String folderpath=ConfigReader.getValueFromPropertyFile("FilePath");
    String filepath=System.getProperty("user.dir") + folderpath;
    ArrayList<String> data = new ArrayList<String>();
	String host=ConfigReader.getValueFromPropertyFile("Typicode_Host");
	
	@Test
	public void getExcelDataTest() {
		excel = new ExcelReaderTest(); //Excel_Reader Class
		
		for (int i=1;i<=4;i++) {
		data=excel.getData(filepath,"testData.xlsx", "getData",i);
		String uri=data.get(0);
		String name=data.get(1);
		String username=data.get(2);
		String email=data.get(3);
		
	Response response=
			given().
			when().
	        get(host+uri).
	        then().
	        statusCode(200).
	        body("name", equalTo(name)).
	        body("username", equalTo(username)).
	        body("email", equalTo(email)).
	        extract().response();
	
	String resp=response.asString();
    System.out.println("Response is:" +resp);
	}
 }
}
