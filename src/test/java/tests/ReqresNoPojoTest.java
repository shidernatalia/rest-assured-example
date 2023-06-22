package tests;

import com.fasterxml.jackson.databind.util.JSONPObject;
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

/**
 * In tests was used https://reqres.in which is a simulation of real application scenarios
 * This approach might be useful when need to validate just a few properties from received response
 */
public class ReqresNoPojoTest {
    private final static String URL = "https://reqres.in";

    /**
     * Validate response JSON file
     * 1. root property "page" equals 2
     * 2. All values in data list are not null
     * 3. Each avatar property value contains the id value
     * 4. All emails end with @reqres.in
     */
    @Test
    public void checkAvatarNoPojoTest(){
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
                .body(("data.avatar"), notNullValue())
                ;
        JsonPath jsonPath = response.extract().jsonPath();
        List<String> emails = jsonPath.getList("data.email");
        List<Integer> ids = jsonPath.getList("data.id");
        List<String> avatars = jsonPath.getList("data.avatar");

        for (int i = 0; i < avatars.size(); i++){
            Assertions.assertTrue(avatars.get(i).contains(ids.get(i).toString()));
        }

        Assertions.assertTrue(emails.stream().allMatch((email -> email.endsWith("@reqres.in"))));
    }

    @Test
    public void successfulRegistrationNoPojoTest(){
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

    @Test
    public void unsuccessfulRegistrationTest(){
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
