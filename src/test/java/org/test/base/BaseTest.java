package org.test.base;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.test.configReader.ConfigReader;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

/**
 * Base test class providing common setup for all API tests
 */
@Listeners(org.test.Listener.ListenerTest.class)
public class BaseTest {

    protected RequestSpecification requestSpec;

    @BeforeClass
    public void setUp() {
        requestSpec = new RequestSpecBuilder()
                .setContentType("application/json")
                .build();
    }

    /**
     * Get base URL from config
     */
    protected String getBaseUrl(String configKey) {
        return ConfigReader.getValueFromPropertyFile(configKey);
    }

    /**
     * Set base URI for RestAssured
     */
    protected void setBaseURI(String baseUrl) {
        RestAssured.baseURI = baseUrl;
    }

    /**
     * Reset RestAssured after each test
     */
    protected void resetBaseURI() {
        RestAssured.reset();
    }
}

