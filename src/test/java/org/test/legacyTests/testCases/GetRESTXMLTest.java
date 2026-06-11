package org.test.legacyTests.testCases;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class GetRESTXMLTest {

    String url = "http://ergast.com/api/f1/2017/circuits.xml";

    @Test
    public void getRESTXMLTest(){

        given(). 
        when().
            get(url).
        then().
            statusCode(200).
            header("Content-Type", "application/xml; charset=utf-8").
            body("MRData.CircuitTable.Circuit[0].CircuitName", equalTo("Albert Park Grand Prix Circuit")).
            body("MRData.CircuitTable.Circuit[0].Location.Locality", equalTo("Melbourne")).
            log().all();
    }
}
