package tests;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import specs.Specifications;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class RestAssuredNoPojoTest {
    private final static String URL = "https://reqres.in";

    /**
     * 1. Get list of users from second page
     * 2. Validate response JSON file
     *      2.1. root property "page" equals 2
     *      2.2. all values in data list are not null
     *      2.3. each avatar property value contains the id value
     *      2.4. all emails end with "@reqres.in"
     */
    @Test
    public void checkAvatarNoPojoTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUnique(200));
        ValidatableResponse response = given()
                .when()
                .get("/api/users?page=2")
                .then().log().all()
                .body("page", equalTo(2))
                .body(("data.id"), notNullValue())
                .body(("data.email"), notNullValue())
                .body(("data.first_name"), notNullValue())
                .body(("data.last_name"), notNullValue())
                .body(("data.avatar"), notNullValue());
        JsonPath jsonPath = response.extract().jsonPath();
        List<String> emails = jsonPath.getList("data.email");
        List<Integer> ids = jsonPath.getList("data.id");
        List<String> avatars = jsonPath.getList("data.avatar");

        for (int i = 0; i < avatars.size(); i++) {
            Assertions.assertTrue(avatars.get(i).contains(ids.get(i).toString()));
        }

        Assertions.assertTrue(emails.stream().allMatch((email -> email.endsWith("@reqres.in"))));
    }

    @Test
    public void successfulRegistrationNoPojoTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUnique(200));
        Map<String, String> user = new HashMap<>();
        user.put("email", "eve.holt@reqres.in");
        user.put("password", "pistol");
        Response response = given()
                .body(user)
                .when()
                .post("/api/register")
                .then().log().all()
//                .body("id", equalTo(4))
//                .body("token", equalTo("QpwL5tke4Pnpja7X4"))
                .extract().response();
        JsonPath jsonPath = response.jsonPath();

        Assertions.assertEquals(4, (int) jsonPath.get("id"));
        Assertions.assertEquals("QpwL5tke4Pnpja7X4", jsonPath.get("token"));
    }

    /**
     * Send
     */
    @Test
    public void unsuccessfulRegistrationTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUnique(400));
        JSONObject jsonpObject = new JSONObject();
        jsonpObject.put("email", "sydney@fife");
        Response response = given()
                .body(jsonpObject)
                .when()
                .post("/api/register")
                .then().log().all()
//                .body("error", equalTo("Missing email or username"))
                .extract()
                .response();
        JsonPath jsonPath = response.jsonPath();
        Assertions.assertEquals("Missing email or username", jsonPath.get("error"));
    }
}
