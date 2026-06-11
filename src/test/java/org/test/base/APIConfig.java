package org.test.base;

import org.test.configReader.ConfigReader;

/**
 * Centralized configuration for all API endpoints and hosts
 */
public class APIConfig {

    // Hosts/Base URLs
    public static final String BOOKS_API_HOST = ConfigReader.getValueFromPropertyFile("BooksAPI_Host");
    public static final String DUMMY_REST_HOST = ConfigReader.getValueFromPropertyFile("DummyRest_Host");
    public static final String TYPICODE_HOST = ConfigReader.getValueFromPropertyFile("Typicode_Host");
    public static final String ERGAST_HOST = ConfigReader.getValueFromPropertyFile("Ergast_Host");
    public static final String THOMAS_BAYER_HOST = ConfigReader.getValueFromPropertyFile("ThomasBayer_Host");

    // Books API Endpoints
    public static final String BOOKS_ALL = "/books";
    public static final String BOOKS_BY_ISBN = "/books/{isbn}";
    public static final String BOOKS_CREATE = "/books";

    // Dummy REST Endpoints
    public static final String DUMMY_CREATE_EMPLOYEE = "/api/v1/create";
    public static final String DUMMY_UPDATE_EMPLOYEE = "/api/v1/update/{id}";
    public static final String DUMMY_DELETE_EMPLOYEE = "/api/v1/delete/{id}";

    // Typicode Endpoints
    public static final String POSTS_ALL = "/posts";
    public static final String POST_BY_ID = "/posts/{id}";
    public static final String USERS_ALL = "/users";
    public static final String USER_BY_ID = "/users/{id}";

    // External API Endpoints
    public static final String REQRES_USERS = "https://reqres.in/api/users";
    public static final String ERGAST_CIRCUITS_JSON = "/api/f1/2017/circuits.json";
    public static final String ERGAST_CIRCUITS_XML = "/api/f1/2017/circuits.xml";
    public static final String THOMAS_BAYER_INVOICE = "/sqlrest/INVOICE";

    // Test Data
    public static final String TEST_FILE_PATH = System.getProperty("user.dir") + "\\Resources\\Data\\";
    public static final String TEST_DATA_FILE = "testData.xlsx";

    // Response Content Types
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String CONTENT_TYPE_XML = "application/xml; charset=utf-8";
}

