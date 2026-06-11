# Test Framework Migration Guide

Welcome to the refactored and consolidated test suite! This guide will help you understand the new structure and how to build upon it.

---

## Quick Start

### Understanding the New Structure

```
src/test/java/org/test/
├── base/                          # Infrastructure Layer
│   ├── APIConfig.java             # All endpoints and hosts
│   ├── APIHelper.java             # Reusable API operations
│   ├── BaseTest.java              # Base class for tests
│   └── TestDataProvider.java      # Test data management
│
└── tests/                         # Test Layer
    ├── api/                       # API-specific tests
    │   ├── BooksAPITest.java
    │   ├── DummyRestAPITest.java
    │   └── ExternalAPIsTest.java
    └── advanced/                  # Advanced feature tests
        ├── AdvancedAssertionsTest.java
        └── POJOSerializationTest.java
```

---

## Key Infrastructure Classes

### 1. APIConfig.java - Your Configuration Hub

**Purpose**: Single source of truth for all API endpoints, hosts, and constants

**What it provides**:
- Base URLs/Hosts
- API endpoints
- Content types
- Test data file paths

**Example Usage**:
```java
// Get base URL
String url = APIConfig.BOOKS_API_HOST;

// Get endpoint
String endpoint = APIConfig.BOOKS_ALL;
String fullUrl = url + endpoint;

// Get test data path
String path = APIConfig.TEST_FILE_PATH;
```

### 2. BaseTest.java - Shared Setup

**Purpose**: Common test setup and initialization

**What it provides**:
- Automatic listener configuration
- Base URI management
- Common setup methods
- Consistent test lifecycle

**Example Usage**:
```java
public class MyAPITest extends BaseTest {
    @BeforeClass
    public void setup() {
        setBaseURI(APIConfig.MY_API_HOST);
    }
    
    @Test
    public void myTest() {
        // Listener already applied, setup done
    }
}
```

### 3. TestDataProvider.java - Data Creation

**Purpose**: Centralized test data creation

**What it provides**:
- Excel data reading
- Data builder methods
- Default test data
- Data factory pattern

**Example Usage**:
```java
// Get default employee data
Map<String, String> data = TestDataProvider.getDefaultEmployeeData();

// Create custom data
Map<String, String> custom = TestDataProvider.createEmployeeData(
    "John Doe", "150000", "35"
);

// Read Excel data
ArrayList<String> excelData = TestDataProvider.getExcelData(
    "sheetName", 1
);
```

### 4. APIHelper.java - Common API Operations

**Purpose**: Reusable API operations to reduce code duplication

**What it provides**:
- GET/POST/PUT/DELETE methods
- Base URI handling
- Response extraction
- Content-type management

**Example Usage**:
```java
// Simple GET
Response response = APIHelper.get("/endpoint");

// POST with body
Response response = APIHelper.post(
    "/endpoint", 
    requestBody
);

// With custom base URI
Response response = APIHelper.getWithBaseUri(
    "https://api.example.com",
    "/endpoint"
);
```

---

## Existing Test Files

### BooksAPITest.java
Tests for Books API CRUD operations

**What it covers**:
- GET all books
- GET book by ISBN
- POST new book
- PUT update book
- DELETE book

**How to use**:
```java
// All tests are grouped by HTTP method
@Test(groups = {"get", "smoke"})
public void testGetAllBooks() { ... }

// Run only GET tests
mvn test -Dtest=BooksAPITest -Dgroups=get
```

### DummyRestAPITest.java
Tests for DummyRest Employee API

**What it covers**:
- POST create employee
- PUT update employee
- DELETE delete employee

**How to use**:
```java
// Tests use centralized data
@Test
public void testCreateEmployee() {
    Map<String, String> data = 
        TestDataProvider.getDefaultEmployeeData();
    // Test implementation...
}
```

### ExternalAPIsTest.java
Tests for multiple external APIs (Typicode, Reqres, Ergast, Thomas Bayer)

**What it covers**:
- Typicode JSON operations
- Ergast Formula 1 data (JSON & XML)
- Reqres user management
- Thomas Bayer data

**How to use**:
```java
// Tests grouped by API
@Test(groups = {"typicode", "smoke"})
public void testGetTypicodePost() { ... }

// Run Typicode tests only
mvn test -Dtest=ExternalAPIsTest -Dgroups=typicode
```

### AdvancedAssertionsTest.java
Demonstrates assertion patterns and best practices

**What it covers**:
- Basic field assertions
- String assertions
- Numeric assertions
- Array/collection assertions
- Combined assertions (allOf, anyOf)
- Key existence checking
- Response extraction

**How to use**:
```java
// Reference for assertion patterns
@Test
public void testAdvancedAssertions() {
    given()
        .baseUri("https://jsonplaceholder.typicode.com")
    .when()
        .get("/posts/1")
    .then()
        .body("id", allOf(greaterThan(0), lessThan(100)))
        .body("userId", anyOf(equalTo(1), equalTo(2)));
}
```

### POJOSerializationTest.java
Tests JSON serialization/deserialization with POJOs

**What it covers**:
- Serialize POJO to JSON
- Deserialize JSON to POJO
- Validation with POJOs

**How to use**:
```java
// Create POJO and POST
PostPayLoad payload = new PostPayLoad(1, 1, "Title", "Body");
given()
    .body(payload)
.when()
    .post("/endpoint");

// Deserialize response to POJO
PostPayLoad post = response.as(PostPayLoad.class);
String title = post.getTitle();
```

---

## How to Add a New API Test

### Step 1: Add Configuration to APIConfig.java

```java
// Add host
public static final String MY_NEW_API_HOST = 
    ConfigReader.getValueFromPropertyFile("MyNewAPI_Host");

// Add endpoints
public static final String NEW_RESOURCE_LIST = "/resources";
public static final String NEW_RESOURCE_BY_ID = "/resources/{id}";
```

### Step 2: Create Test Class

```java
package org.test.tests.api;

import org.test.base.APIConfig;
import org.test.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class MyNewAPITest extends BaseTest {
    
    @BeforeClass
    public void setupMyNewAPI() {
        setBaseURI(APIConfig.MY_NEW_API_HOST);
    }
    
    @Test(groups = {"mynewapi", "smoke"})
    public void testGetResources() {
        given()
        .when()
            .get(APIConfig.NEW_RESOURCE_LIST)
        .then()
            .statusCode(200)
            .log().all();
    }
    
    @Test(groups = {"mynewapi"})
    public void testGetResourceById() {
        given()
        .when()
            .get(APIConfig.NEW_RESOURCE_BY_ID.replace("{id}", "1"))
        .then()
            .statusCode(200)
            .body("id", equalTo(1))
            .log().all();
    }
}
```

### Step 3: Run Your Tests

```bash
# Run all new API tests
mvn test -Dtest=MyNewAPITest

# Run by group
mvn test -Dtest=MyNewAPITest -Dgroups=smoke

# Run specific test
mvn test -Dtest=MyNewAPITest#testGetResources
```

---

## Best Practices

### 1. Use Test Groups
```java
// Always group your tests for better organization
@Test(groups = {"get", "smoke"}, 
      description = "Get user by ID")
public void testGetUserById() { ... }
```

### 2. Use Descriptive Names
```java
// ❌ Bad
@Test
public void test1() { ... }

// ✅ Good
@Test(description = "Create employee and verify salary")
public void testCreateEmployeeWithValidation() { ... }
```

### 3. Use APIConfig for Constants
```java
// ❌ Bad
given().get("http://127.0.0.1:5000/books").then()...

// ✅ Good
given()
    .baseUri(APIConfig.BOOKS_API_HOST)
    .when()
    .get(APIConfig.BOOKS_ALL)
    .then()...
```

### 4. Use TestDataProvider
```java
// ❌ Bad
Map<String, String> data = new HashMap<>();
data.put("name", "Simran");
data.put("salary", "140000");

// ✅ Good
Map<String, String> data = TestDataProvider.getDefaultEmployeeData();
```

### 5. Extend BaseTest
```java
// ❌ Bad
public class MyTest {
    // Missing listener, no setup
}

// ✅ Good
public class MyTest extends BaseTest {
    // Listener applied automatically, setup available
}
```

### 6. Use Fluent API Style
```java
// Consistent with rest-assured best practices
given()
    .contentType(APIConfig.CONTENT_TYPE_JSON)
    .body(requestData)
.when()
    .post(endpoint)
.then()
    .statusCode(201)
    .body("status", equalTo("success"))
    .log().all();
```

---

## Running Tests

### By Test Class
```bash
mvn test -Dtest=BooksAPITest
mvn test -Dtest=ExternalAPIsTest
mvn test -Dtest=AdvancedAssertionsTest
```

### By Group
```bash
mvn test -Dgroups=smoke              # Smoke tests
mvn test -Dgroups=get                # GET tests
mvn test -Dgroups=api                # All API tests
mvn test -Dgroups=advanced           # Advanced tests
mvn test -Dgroups=typicode           # Typicode tests only
mvn test -Dgroups=ergast             # Ergast tests only
```

### By Multiple Criteria
```bash
mvn test -Dtest=ExternalAPIsTest -Dgroups=typicode
mvn test -Dgroups="get,post,smoke"
```

### Full Suite
```bash
mvn test                             # All tests
mvn clean install                    # Build and test
```

---

## Common Tasks

### How to add a new endpoint?
1. Add to `APIConfig.java`
2. Reference in test classes
3. No changes needed elsewhere

### How to change an endpoint URL?
1. Update in `APIConfig.java` only
2. All tests automatically use new URL

### How to add test data?
1. Add method to `TestDataProvider.java`
2. Use in tests via `TestDataProvider.methodName()`

### How to change test data setup?
1. Update `TestDataProvider.java`
2. All tests using that data automatically updated

### How to run only failing tests?
```bash
mvn test -Dtest=TestClass#testMethod
```

### How to enable detailed logging?
```bash
mvn test -Dorg.slf4j.simpleLogger.defaultLogLevel=debug
```

---

## File Locations

| File | Location | Purpose |
|------|----------|---------|
| Configuration | `src/test/java/org/test/base/APIConfig.java` | Centralized config |
| Test Data | `src/test/java/org/test/base/TestDataProvider.java` | Data management |
| Base Class | `src/test/java/org/test/base/BaseTest.java` | Common setup |
| API Helper | `src/test/java/org/test/base/APIHelper.java` | Utilities |
| Properties | `Resources/ConfigFiles/Env.properties` | Config values |
| Test Data Excel | `Resources/Data/testData.xlsx` | Test data file |

---

## Troubleshooting

### Test not finding config value
- Check: Value exists in `Env.properties`
- Check: Key name matches in `APIConfig.java`
- Check: Property file is in Resources

### POJO deserialization failing
- Ensure class has getters/setters
- Check: JSON field names match class properties
- Use: `@JsonProperty` annotation if names don't match

### Test running but not assertions
- Ensure assertions are in `.then()` block
- Check: Use correct matcher from Hamcrest
- Log response with `.log().all()`

### Base URI issues
- Ensure `setBaseURI()` called in `@BeforeClass`
- Check: URI is complete and valid
- Verify: No trailing slash issues

---

## Support

For questions or issues:
1. Check this guide
2. Review example test classes
3. Check assertion examples in `AdvancedAssertionsTest`
4. Refer to `TEST_STRUCTURE.md` for architecture
5. Refer to `CLEANUP_SUMMARY.md` for what changed

---

**Last Updated**: June 11, 2026  
**Page Version**: 1.0  
**Status**: Production Ready

