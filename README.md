Few Interview Questions:
**Q) Have you worked with Rest Assured or Postman for API automation?
Rest Assured**
Used for:
GET
POST
PUT
DELETE
Authentication
JSON validation

Example:
given()
.when()
    .get("/users/1")
.then()
    .statusCode(200);
Postman

Used for:
API exploration
Request collections
Environment variables
Quick validation
Integration

API creates test data before UI tests run.

**Q) How do you validate a JSON response using Java?
Rest Assured**
_given()
.when()
.get("/users/1")
.then()
.body("name", equalTo("John"));_

JsonPath
_JsonPath json = response.jsonPath();
String name = json.getString("name");
Assert.assertEquals(name,"John");_

Jackson ObjectMapper
_ObjectMapper mapper = new ObjectMapper();
User user = mapper.readValue(jsonString, User.class);_
